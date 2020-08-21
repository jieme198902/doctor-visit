package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusGoodsSpecification;
import com.doctor.visit.domain.BusSelfDiagnose;
import com.doctor.visit.domain.BusSelfDiagnosis;
import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.repository.BusSelfDiagnoseMapper;
import com.doctor.visit.repository.BusSelfDiagnosisMapper;
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
import java.util.Optional;

/**
 * 自诊
 */
@Service
public class SelfDiagnosticsService {
    private final CommonService commonService;
    //
    private final BusSelfDiagnoseMapper busSelfDiagnoseMapper;
    private final BusSelfDiagnosisMapper busSelfDiagnosisMapper;

    public SelfDiagnosticsService(CommonService commonService, BusSelfDiagnoseMapper busSelfDiagnoseMapper, BusSelfDiagnosisMapper busSelfDiagnosisMapper) {
        this.commonService = commonService;
        this.busSelfDiagnoseMapper = busSelfDiagnoseMapper;
        this.busSelfDiagnosisMapper = busSelfDiagnosisMapper;
    }

    /**
     * 获取自诊问题列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusSelfDiagnose>> listSelfDiagnose(BusSelfDiagnose bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Example example = new Example(BusSelfDiagnose.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDel", Constants.EXIST);
        if (null != bus.getId()) {
            criteria.andEqualTo("fid", bus.getId());
        }
        if (StringUtils.isNotBlank(bus.getContent())) {
            criteria.andLike("content", bus.getContent() + "%");
        }
        Page<BusSelfDiagnose> busList = (Page<BusSelfDiagnose>) busSelfDiagnoseMapper.selectByExample(example);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 新增或者更新自诊问题
     *
     * @param bus
     * @param request
     * @return
     */
    public ComResponse<BusSelfDiagnose> insertOrUpdateSelfDiagnose(BusSelfDiagnose bus, HttpServletRequest request) {
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
                busSelfDiagnoseMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateTime(new Date());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                busSelfDiagnoseMapper.insertSelective(bus);
            }
        } else {
            return ComResponse.failUnauthorized();
        }
        return ComResponse.ok(bus);
    }


    /**
     * 根据id删除自诊问题
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteSelfDiagnose(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusSelfDiagnose delRecord = new BusSelfDiagnose();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busSelfDiagnoseMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }


    /**
     * 获取自诊结果列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusSelfDiagnosis>> listSelfDiagnosis(BusSelfDiagnosis bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Example example = new Example(BusSelfDiagnosis.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDel", Constants.EXIST);

        if (StringUtils.isNotBlank(bus.getReason())) {
            criteria.andLike("reason", bus.getReason() + "%");
        }
        Page<BusSelfDiagnosis> busList = (Page<BusSelfDiagnosis>) busSelfDiagnosisMapper.selectByExample(example);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 新增或者更新自诊结果
     *
     * @param bus
     * @param request
     * @return
     */
    public ComResponse<BusSelfDiagnosis> insertOrUpdateSelfDiagnosis(BusSelfDiagnosis bus, HttpServletRequest request) {
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
                busSelfDiagnosisMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateTime(new Date());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                busSelfDiagnosisMapper.insertSelective(bus);
            }
        } else {
            return ComResponse.failUnauthorized();
        }
        return ComResponse.ok(bus);
    }


    /**
     * 根据id删除自诊结果
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteSelfDiagnosis(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusSelfDiagnosis delRecord = new BusSelfDiagnosis();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busSelfDiagnosisMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }


}
