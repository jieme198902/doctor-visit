package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.domain.SysMenu;
import com.doctor.visit.domain.SysRole;
import com.doctor.visit.repository.SysMenuMapper;
import com.doctor.visit.repository.SysRoleMapper;
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

@Service
public class SysAuthService {

    private final CommonService commonService;
    //
    private final SysRoleMapper sysRoleMapper;
    private final SysMenuMapper sysMenuMapper;

    public SysAuthService(CommonService commonService, SysRoleMapper sysRoleMapper, SysMenuMapper sysMenuMapper) {
        this.commonService = commonService;
        this.sysRoleMapper = sysRoleMapper;
        this.sysMenuMapper = sysMenuMapper;
    }

    /**
     * 角色列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<SysRole>> listRole(SysRole bus, Pageable pageable) {
        if (null != pageable) {
            PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        }
        SysRole record = new SysRole();
        if (StringUtils.isNotBlank(bus.getName())) {
            record.setName(bus.getName());
        }
        if (null != pageable) {
            Page<SysRole> busList = (Page<SysRole>) sysRoleMapper.select(record);
            return ComResponse.ok(busList.getResult(), busList.getTotal());
        } else {
            return ComResponse.ok(sysRoleMapper.select(record));
        }
    }


    /**
     * 更新或者修改角色
     *
     * @param bus
     * @param request
     * @return
     */
    public ComResponse<SysRole> insertOrUpdateRole(SysRole bus, HttpServletRequest request) {
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
                sysRoleMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateTime(new Date());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                sysRoleMapper.insertSelective(bus);
            }
            return ComResponse.ok(bus);
        } else {
            return ComResponse.failUnauthorized();
        }
    }

    /**
     * 根据id删除角色
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteRole(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            SysRole delRecord = new SysRole();
            delRecord.setId(Long.parseLong(id));
            int i = sysRoleMapper.deleteByPrimaryKey(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

    /////////////////////
    /**
     * 角色列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<SysMenu>> listMenu(SysMenu bus, Pageable pageable) {
        if (null != pageable) {
            PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        }
        SysMenu record = new SysMenu();
        if (StringUtils.isNotBlank(bus.getTitle())) {
            record.setTitle(bus.getTitle());
        }
        if (null != pageable) {
            Page<SysMenu> busList = (Page<SysMenu>) sysMenuMapper.select(record);
            return ComResponse.ok(busList.getResult(), busList.getTotal());
        } else {
            return ComResponse.ok(sysMenuMapper.select(record));
        }
    }


    /**
     * 更新或者修改菜单
     *
     * @param bus
     * @param request
     * @return
     */
    public ComResponse<SysMenu> insertOrUpdateMenu(SysMenu bus, HttpServletRequest request) {
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
                sysMenuMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateTime(new Date());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                sysMenuMapper.insertSelective(bus);
            }
            return ComResponse.ok(bus);
        } else {
            return ComResponse.failUnauthorized();
        }
    }

    /**
     * 根据id删除菜单
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteMenu(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            SysMenu delRecord = new SysMenu();
            delRecord.setId(Long.parseLong(id));
            int i = sysRoleMapper.deleteByPrimaryKey(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

}
