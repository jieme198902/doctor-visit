package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.repository.BusClincClassMapper;
import com.doctor.visit.repository.BusDoctorMapper;
import com.doctor.visit.repository.BusHospitalMapper;
import com.doctor.visit.repository.BusRelationUserDoctorMapper;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 医生的业务层
 */
@Service
public class DoctorService {

    private final CommonService commonService;
    private final UploadService uploadService;
    //
    private final BusDoctorMapper busDoctorMapper;
    private final BusHospitalMapper busHospitalMapper;
    private final BusClincClassMapper busClincClassMapper;
    private final BusRelationUserDoctorMapper busRelationUserDoctorMapper;

    public DoctorService(CommonService commonService, UploadService uploadService, BusDoctorMapper busDoctorMapper, BusHospitalMapper busHospitalMapper, BusClincClassMapper busClincClassMapper, BusRelationUserDoctorMapper busRelationUserDoctorMapper) {
        this.commonService = commonService;
        this.uploadService = uploadService;
        this.busDoctorMapper = busDoctorMapper;
        this.busHospitalMapper = busHospitalMapper;
        this.busClincClassMapper = busClincClassMapper;
        this.busRelationUserDoctorMapper = busRelationUserDoctorMapper;
    }

    /**
     * 获取医生列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusDoctor>> listDoctor(BusDoctor bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Example example = new Example(BusDoctor.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(bus.getName())) {
            criteria.andLike("name", bus.getName() + "%");
        }
        if (null != bus.getClincId()) {
            criteria.andEqualTo("clincId", bus.getClincId());
        }

        criteria.andEqualTo("isDel", Constants.EXIST);

        Page<BusDoctor> busList = (Page<BusDoctor>) busDoctorMapper.selectByExample(example);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 前台 - 获取关注的医生列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusDoctor>> listFavDoctor(BusDoctor bus, Pageable pageable, HttpServletRequest request) throws Exception {
        //获取用户的id
        Long userId = Utils.getUserId(request);
        bus.setCreateBy(userId);
        if (null == bus.getCreateBy()) {
            return ComResponse.failBadRequest();
        }
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Page<BusDoctor> busList = (Page<BusDoctor>) busDoctorMapper.selectFavDoctor(bus.getCreateBy());
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }


    /**
     * 新增或者更新医生
     *
     * @param bus
     * @param request 这里需要处理文件
     * @return
     */
    public ComResponse<BusDoctor> insertOrUpdateDoctor(BusDoctor bus, HttpServletRequest request) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            //根据医院名称，查询医院
            BusHospital hospital = new BusHospital();
            hospital.setName(bus.getHospitalName());
            hospital.setIsDel(Constants.EXIST);
            List<BusHospital> hospitals = busHospitalMapper.select(hospital);
            if (hospitals.isEmpty()) {
                return ComResponse.fail("根据医院名称未找医院信息，请先维护医院信息");
            }
            if (hospitals.size() > 1) {
                return ComResponse.fail("根据医院名称找到多个医院信息，请维护正确的医院信息");
            }
            bus.setHospitalName(hospitals.get(0).getName());
            bus.setHospitalId(hospitals.get(0).getId());
            //根据科室名称，查询科室
            BusClincClass clincClass = new BusClincClass();
            clincClass.setClincClassName(bus.getClincName());
            List<BusClincClass> clincClasses = busClincClassMapper.select(clincClass);
            if (clincClasses.isEmpty()) {
                return ComResponse.fail("根据科室名称未找科室信息，请先维护科室信息");
            }
            if (clincClasses.size() > 1) {
                return ComResponse.fail("根据科室名称找到多个科室信息，请维护正确的科室信息");
            }
            bus.setClincName(clincClasses.get(0).getClincClassName());
            bus.setClincId(clincClasses.get(0).getId());
            //
            bus.setEditTime(new Date());
            bus.setEditBy(jhiUser.getId());
            bus.setEditName(jhiUser.getFirstName());
            if (null != bus.getId()) {
                busDoctorMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateTime(new Date());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                busDoctorMapper.insertSelective(bus);
            }
            BusFile busFile = new BusFile();
            busFile.setBus("bus_doctor");
            busFile.setBusId(bus.getId());
            busFile.setFileType(Constants.FILE_TYPE_IMG);
            uploadService.uploadFiles(busFile, request);
        } else {
            return ComResponse.failUnauthorized();
        }

        return ComResponse.ok(bus);
    }


    /**
     * 根据id删除医院
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteDoctor(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusDoctor delRecord = new BusDoctor();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busDoctorMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

    /**
     * 前台 - 删除或者修改用户关注的医生
     *
     * @param bus
     * @return
     */
    public ComResponse insertOrUpdateRelationUserDoctor(BusRelationUserDoctor bus, HttpServletRequest request) throws Exception {
        //获取用户的id
        if (null == bus.getDoctorId()) {
            return ComResponse.failBadRequest();
        }
        Long userId = Utils.getUserId(request);
        if (null == bus.getId()) {
            bus.setId(IDKeyUtil.generateId());
            bus.setUserId(userId);
            busRelationUserDoctorMapper.insertSelective(bus);
        } else {
            bus.setUserId(userId);
            busRelationUserDoctorMapper.updateByPrimaryKeySelective(bus);
        }
        return ComResponse.ok();
    }
}
