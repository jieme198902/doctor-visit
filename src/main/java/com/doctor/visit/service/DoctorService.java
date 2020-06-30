package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusDoctor;
import com.doctor.visit.domain.BusRelationUserDoctor;
import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.repository.BusDoctorMapper;
import com.doctor.visit.repository.BusRelationUserDoctorMapper;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    //
    private final BusDoctorMapper busDoctorMapper;
    private final BusRelationUserDoctorMapper busRelationUserDoctorMapper;

    public DoctorService(CommonService commonService, BusDoctorMapper busDoctorMapper, BusRelationUserDoctorMapper busRelationUserDoctorMapper) {
        this.commonService = commonService;
        this.busDoctorMapper = busDoctorMapper;
        this.busRelationUserDoctorMapper = busRelationUserDoctorMapper;
    }

    /**
     * 前台 - 获取医生列表
     * FIXME 关注状态
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusDoctor>> listDoctor(BusDoctor bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        bus.setIsDel(Constants.EXIST);
        Page<BusDoctor> busList = (Page<BusDoctor>) busDoctorMapper.select(bus);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 前台 - 获取关注的医生列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusDoctor>> listFavDoctor(BusDoctor bus, Pageable pageable) {
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
     * @param request   这里需要处理文件
     * @return
     */
    public ComResponse<BusDoctor> insertOrUpdateDoctor(BusDoctor bus, HttpServletRequest request) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            bus.setEditTime(new Date());
            bus.setEditBy(jhiUser.getId());
            bus.setEditName(jhiUser.getFirstName());
            if (null != bus.getId()) {
                busDoctorMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                busDoctorMapper.insertSelective(bus);
            }
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
    public ComResponse insertOrUpdateRelationUserDoctor(BusRelationUserDoctor bus) {
        if (null == bus.getId()) {
            bus.setId(IDKeyUtil.generateId());
            busRelationUserDoctorMapper.insertSelective(bus);
        } else {
            busRelationUserDoctorMapper.updateByPrimaryKeySelective(bus);
        }
        return ComResponse.ok();
    }
}
