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
     * @param busDoctor
     * @param pageable
     * @return
     */
    public ComResponse<List<BusDoctor>> listDoctor(BusDoctor busDoctor, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        busDoctor.setIsDel(Constants.EXIST);
        Page<BusDoctor> busList = (Page<BusDoctor>) busDoctorMapper.select(busDoctor);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 前台 - 获取关注的医生列表
     *
     * @param busDoctor
     * @param pageable
     * @return
     */
    public ComResponse<List<BusDoctor>> listFavDoctor(BusDoctor busDoctor, Pageable pageable) {
        if (null == busDoctor.getCreateBy()) {
            return ComResponse.failBadRequest();
        }
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Page<BusDoctor> busList = (Page<BusDoctor>) busDoctorMapper.selectFavDoctor(busDoctor.getCreateBy());
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }


    /**
     * 新增或者更新医生
     *
     * @param busDoctor
     * @param request   这里需要处理文件
     * @return
     */
    public ComResponse<BusDoctor> insertOrUpdateDoctor(BusDoctor busDoctor, HttpServletRequest request) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            busDoctor.setEditTime(new Date());
            busDoctor.setEditBy(jhiUser.getId());
            busDoctor.setEditName(jhiUser.getFirstName());
            if (null != busDoctor.getId()) {
                busDoctorMapper.updateByPrimaryKeySelective(busDoctor);
            } else {
                busDoctor.setId(IDKeyUtil.generateId());
                busDoctorMapper.insertSelective(busDoctor);
            }
        } else {
            return ComResponse.failUnauthorized();
        }

        return ComResponse.ok(busDoctor);
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
     * @param busRelationUserDoctor
     * @return
     */
    public ComResponse insertOrUpdateRelationUserDoctor(BusRelationUserDoctor busRelationUserDoctor) {
        if (null == busRelationUserDoctor.getId()) {
            busRelationUserDoctor.setId(IDKeyUtil.generateId());
            busRelationUserDoctorMapper.insertSelective(busRelationUserDoctor);
        } else {
            busRelationUserDoctorMapper.updateByPrimaryKeySelective(busRelationUserDoctor);
        }
        return ComResponse.ok();
    }
}
