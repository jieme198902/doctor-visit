package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusHospital;
import com.doctor.visit.domain.BusPatient;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.repository.BusPatientMapper;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 患者信息 业务层
 */
@Service
public class PatientService {

    private final CommonService commonService;
    private final BusPatientMapper busPatientMapper;

    public PatientService(CommonService commonService, BusPatientMapper busPatientMapper) {
        this.commonService = commonService;
        this.busPatientMapper = busPatientMapper;
    }

    /**
     * 前台 - 获取患者列表
     *
     * @param busPatient
     * @param pageable
     * @return
     */
    public ComResponse<List<BusPatient>> listPatient(BusPatient busPatient, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        busPatient.setIsDel(Constants.EXIST);
        Example example = new Example(BusPatient.class);
        Example.Criteria criteria = example.createCriteria();
        //患者名字
        if (StringUtils.isNotBlank(busPatient.getName())) {
            criteria.andLike("name", busPatient.getName() + "%");
        }
        //创建者
        if (null != busPatient.getCreateBy()) {
            criteria.andEqualTo("createBy", busPatient.getCreateBy());
        }

        Page<BusPatient> busList = (Page<BusPatient>) busPatientMapper.selectByExample(example);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 前台 - 新增或者更新患者
     *
     * @param busPatient
     * @param request
     * @return
     */
    public ComResponse<BusPatient> insertOrUpdatePatient(BusPatient busPatient, HttpServletRequest request) {
        if (null == busPatient.getCreateBy()) {
            return ComResponse.failBadRequest();
        }
        BusUser busUser = commonService.getBusUser(busPatient.getCreateBy());
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }
        busPatient.setEditTime(new Date());
        busPatient.setEditBy(busUser.getId());
        busPatient.setEditName(busUser.getName());
        if (null != busPatient.getId()) {
            busPatientMapper.updateByPrimaryKeySelective(busPatient);
        } else {
            busPatient.setId(IDKeyUtil.generateId());
            busPatient.setCreateTime(new Date());
            busPatient.setCreateBy(busUser.getId());
            busPatient.setCreateName(busUser.getName());
            busPatientMapper.insertSelective(busPatient);
        }

        return ComResponse.ok(busPatient);
    }


    /**
     * 前台 - 根据id删除患者
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deletePatient(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusPatient delRecord = new BusPatient();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busPatientMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }
}
