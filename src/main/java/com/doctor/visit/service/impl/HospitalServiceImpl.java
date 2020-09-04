package com.doctor.visit.service.impl;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.domain.dto.BusHospitalDto;
import com.doctor.visit.repository.BusAreaMapper;
import com.doctor.visit.repository.BusFileMapper;
import com.doctor.visit.repository.BusHospitalMapper;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.service.CommonService;
import com.doctor.visit.service.UploadService;
import com.doctor.visit.web.rest.util.BeanConversionUtil;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 医院的业务层
 *
 * @author kuanwang
 * @date 2020-06-29
 */
@Service
public class HospitalServiceImpl {
    private final CommonService commonService;
    private final UploadService uploadService;
    //
    private final BusHospitalMapper busHospitalMapper;
    private final BusAreaMapper busAreaMapper;
    private final BusFileMapper busFileMapper;
    @Value("${custom.requestPath}")
    private String requestPath;

    public HospitalServiceImpl(CommonService commonService, UploadService uploadService, BusHospitalMapper busHospitalMapper, BusAreaMapper busAreaMapper, BusFileMapper busFileMapper) {
        this.commonService = commonService;
        this.uploadService = uploadService;
        this.busHospitalMapper = busHospitalMapper;
        this.busAreaMapper = busAreaMapper;
        this.busFileMapper = busFileMapper;
    }

    /**
     * 前台 - 获取医院列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusHospitalDto>> listHospital(BusHospital bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Example example = new Example(BusHospital.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("isDel", Constants.EXIST);
        if (StringUtils.isNotBlank(bus.getName())) {
            criteria.andLike("name", bus.getName() + "%");
        }
        Page<BusHospital> busList = null;
        if (StringUtils.isNoneBlank(bus.getLat(), bus.getLng())) {
            busList = (Page<BusHospital>) busHospitalMapper.selectHospitalByDistance(bus);
        } else {
            busList = (Page<BusHospital>) busHospitalMapper.selectByExample(example);
        }

        List<BusHospitalDto> busHospitalDtoList = Lists.newArrayList();
        busList.getResult().forEach(busHospital -> busHospitalDtoList.add(BeanConversionUtil.beanToDto(busHospital, requestPath, busFileMapper)));

        return ComResponse.ok(busHospitalDtoList, busList.getTotal());
    }

    /**
     * 新增或者更新医院
     *
     * @param bus
     * @param request 这里需要处理文件
     * @return
     */
    public ComResponse<BusHospital> insertOrUpdateHospital(BusHospital bus, HttpServletRequest request) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            if (StringUtils.isNotBlank(bus.getAreaCode())) {
                BusArea areaRecord = new BusArea();
                areaRecord.setWgbm(bus.getAreaCode());
                areaRecord = busAreaMapper.selectOne(areaRecord);
                if (null != areaRecord) {
                    bus.setAreaCode(areaRecord.getWgbm());
                    bus.setAreaName(areaRecord.getWgmc());
                }
            }

            bus.setEditTime(new Date());
            bus.setEditBy(jhiUser.getId());
            bus.setEditName(jhiUser.getFirstName());
            if (null != bus.getId()) {
                busHospitalMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateTime(new Date());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                busHospitalMapper.insertSelective(bus);
            }
            BusFile busFile = new BusFile();
            busFile.setBus("bus_hospital");
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
    public ComResponse<StringBuilder> deleteHospital(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusHospital delRecord = new BusHospital();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busHospitalMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

}
