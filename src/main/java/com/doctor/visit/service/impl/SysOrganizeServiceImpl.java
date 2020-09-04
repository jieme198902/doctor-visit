package com.doctor.visit.service.impl;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.repository.SysDeptMapper;
import com.doctor.visit.repository.SysJobMapper;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.service.SysOrganizeService;
import com.doctor.visit.service.common.CommonService;
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
 * 组织机构，岗位，部门
 */
@Service
public class SysOrganizeServiceImpl implements SysOrganizeService {
    private final CommonService commonService;

    private final SysJobMapper sysJobMapper;
    private final SysDeptMapper sysDeptMapper;

    public SysOrganizeServiceImpl(CommonService commonService, SysJobMapper sysJobMapper, SysDeptMapper sysDeptMapper) {
        this.commonService = commonService;
        this.sysJobMapper = sysJobMapper;
        this.sysDeptMapper = sysDeptMapper;
    }

    @Override
    public ComResponse<List<SysDept>> listDept(SysDept bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Example example = new Example(SysDept.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("isDel", Constants.EXIST);
        if (StringUtils.isNotBlank(bus.getName())) {
            criteria.andLike("name", bus.getName() + "%");
        }
        Page<SysDept> busList =  (Page<SysDept>) sysDeptMapper.selectByExample(example);

        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    @Override
    public ComResponse<SysDept> insertOrUpdateDept(SysDept bus, HttpServletRequest request) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            bus.setEditTime(new Date());
            bus.setEditBy(jhiUser.getId());
            bus.setEditName(jhiUser.getFirstName());
            if (null != bus.getDeptId()) {
                sysDeptMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setDeptId(IDKeyUtil.generateId());
                bus.setCreateTime(new Date());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                sysDeptMapper.insertSelective(bus);
            }
        } else {
            return ComResponse.failUnauthorized();
        }
        return ComResponse.ok(bus);
    }

    @Override
    public ComResponse<StringBuilder> deleteDept(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            SysDept delRecord = new SysDept();
            delRecord.setDeptId(Long.parseLong(id));
            delRecord.setIsDel(Constants.DELETE);
            int i = sysDeptMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

    @Override
    public ComResponse<List<SysJob>> listJob(SysJob bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Example example = new Example(SysJob.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("isDel", Constants.EXIST);
        if (StringUtils.isNotBlank(bus.getName())) {
            criteria.andLike("name", bus.getName() + "%");
        }
        if(null!=bus.getEnabled()){
            criteria.andEqualTo("enabled",bus.getEnabled());
        }
        Page<SysJob> busList =  (Page<SysJob>) sysJobMapper.selectByExample(example);

        return ComResponse.ok(busList.getResult(), busList.getTotal());

    }

    @Override
    public ComResponse<SysJob> insertOrUpdateJob(SysJob bus, HttpServletRequest request) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            bus.setEditTime(new Date());
            bus.setEditBy(jhiUser.getId());
            bus.setEditName(jhiUser.getFirstName());
            if (null != bus.getJobId()) {
                sysJobMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setJobId(IDKeyUtil.generateId());
                bus.setCreateTime(new Date());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                sysJobMapper.insertSelective(bus);
            }
        } else {
            return ComResponse.failUnauthorized();
        }
        return ComResponse.ok(bus);
    }

    @Override
    public ComResponse<StringBuilder> deleteJob(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            SysJob delRecord = new SysJob();
            delRecord.setJobId(Long.parseLong(id));
            delRecord.setIsDel(Constants.DELETE);
            int i = sysJobMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }
}
