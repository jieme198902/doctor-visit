package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.domain.dto.SysMenuDto;
import com.doctor.visit.repository.*;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class SysAuthService {

    private final CommonService commonService;
    //
    private final SysRoleMapper sysRoleMapper;
    private final SysMenuMapper sysMenuMapper;
    private final SysButtonMapper sysButtonMapper;
    private final SysPermissionMapper sysPermissionMapper;
    private final SysRelationUserRoleMapper sysRelationUserRoleMapper;
    private final SysRelationMenuButtonMapper sysRelationMenuButtonMapper;

    public SysAuthService(CommonService commonService, SysRoleMapper sysRoleMapper, SysMenuMapper sysMenuMapper, SysButtonMapper sysButtonMapper, SysPermissionMapper sysPermissionMapper, SysRelationUserRoleMapper sysRelationUserRoleMapper, SysRelationMenuButtonMapper sysRelationMenuButtonMapper) {
        this.commonService = commonService;
        this.sysRoleMapper = sysRoleMapper;
        this.sysMenuMapper = sysMenuMapper;
        this.sysButtonMapper = sysButtonMapper;
        this.sysPermissionMapper = sysPermissionMapper;
        this.sysRelationUserRoleMapper = sysRelationUserRoleMapper;
        this.sysRelationMenuButtonMapper = sysRelationMenuButtonMapper;
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
            PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(), "sort_by desc");
        }
        SysRole record = new SysRole();
        if (StringUtils.isNotBlank(bus.getName())) {
            record.setName(bus.getName());
        }
        record.setIsDel(Constants.EXIST);
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
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = sysRoleMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

    /////////////////////

    /**
     * 菜单列表
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
        record.setIsDel(Constants.EXIST);
        if (null != pageable) {
            Page<SysMenu> busList = (Page<SysMenu>) sysMenuMapper.select(record);
            return ComResponse.ok(busList.getResult(), busList.getTotal());
        } else {
            return ComResponse.ok(sysMenuMapper.select(record));
        }
    }

    /**
     * 获取菜单树
     *
     * @param bus
     * @return
     */
    public ComResponse<List<SysMenuDto>> listMenuTree(SysMenu bus) {
        SysMenu record = new SysMenu();
        record.setIsDel("0");
        List<SysMenu> sysMenus = sysMenuMapper.selectMenu(record);
        //把这个集合处理成树状结构
        List<SysMenuDto> treeMenu = Utils.menuListToTree(sysMenus, sysButtonMapper);
        return ComResponse.ok(treeMenu);
    }

    /**
     * 获取菜单树根据角色id
     *
     * @param bus
     * @return
     */
    public ComResponse<List<String>> listMenuByRoleId(SysPermission bus) {
        ///
        if (null == bus.getRoleId()) {
            return ComResponse.failBadRequest();
        }
        List<String> ids = Lists.newArrayList();
        List<SysMenu> sysMenus = sysMenuMapper.selectMenuByRoleId(bus.getRoleId());
        if (null != sysMenus && !sysMenus.isEmpty()) {
            sysMenus.forEach(it -> ids.add(it.getId().toString()));
        }
        //把这个集合处理成树状结构
        return ComResponse.ok(ids);
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
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = sysMenuMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

    /**
     * 根据菜单id获取按钮列表
     *
     * @param sysMenu
     * @return
     */
    public ComResponse<List<SysButton>> selectByMenu(SysMenu sysMenu) {
        if (null != sysMenu.getId()) {
            List<SysButton> sysButtons = sysButtonMapper.selectByMenuId(sysMenu.getId());
            return ComResponse.ok(sysButtons);
        }
        if (StringUtils.isNotBlank(sysMenu.getCode())) {
            List<SysButton> sysButtons = sysButtonMapper.selectByMenuCode(sysMenu.getCode());
            return ComResponse.ok(sysButtons);
        }

        return ComResponse.failBadRequest();
    }

    ///////


    /**
     * 更新或者修改菜单
     *
     * @param bus
     * @param request
     * @return
     */
    public ComResponse<SysPermission> insertOrUpdatePermission(SysPermission bus, String menus, String buttons, HttpServletRequest request) {
        if (null == bus.getRoleId() || StringUtils.isBlank(menus)) {
            return ComResponse.failBadRequest();
        }
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            //直接删除全部
            SysPermission delRecord = new SysPermission();
            delRecord.setRoleId(bus.getRoleId());
            sysPermissionMapper.delete(delRecord);
            //直接添加新的菜单
            String[] menuAry = menus.split(Constants.COMMA);
            for (String menuId : menuAry) {
                SysPermission insertPermission = new SysPermission();
                insertPermission.setId(IDKeyUtil.generateId());
                insertPermission.setRoleId(bus.getRoleId());
                insertPermission.setMenuButtonId(Long.parseLong(menuId));
                insertPermission.setMenuButtonType("0");
                sysPermissionMapper.insertSelective(insertPermission);
            }
            //直接添加新的按钮
            if (StringUtils.isNotBlank(buttons)) {
                String[] buttonAry = buttons.split(Constants.COMMA);
                for (String buttonId : buttonAry) {
                    SysPermission insertPermission = new SysPermission();
                    insertPermission.setId(IDKeyUtil.generateId());
                    insertPermission.setRoleId(bus.getRoleId());
                    insertPermission.setMenuButtonId(Long.parseLong(buttonId));
                    insertPermission.setMenuButtonType("1");
                    sysPermissionMapper.insertSelective(insertPermission);
                }
            }
            return ComResponse.ok();
        } else {
            return ComResponse.failUnauthorized();
        }
    }


    /**
     * 修改用户的角色
     *
     * @param bus
     * @param roles   角色ids
     * @param request
     * @return
     */
    public ComResponse<String> insertOrUpdateUserRole(SysRelationUserRole bus, String roles, HttpServletRequest request) {
        if (null == bus.getUserId() || StringUtils.isBlank(roles)) {
            return ComResponse.failBadRequest();
        }
        //直接删除全部
        SysRelationUserRole delRecord = new SysRelationUserRole();
        delRecord.setUserId(bus.getUserId());
        sysRelationUserRoleMapper.delete(delRecord);
        //保存新的
        String[] roleAry = roles.split(Constants.COMMA);
        for (String role : roleAry) {
            SysRelationUserRole insert = new SysRelationUserRole();
            insert.setId(IDKeyUtil.generateId());
            insert.setUserId(bus.getUserId());
            insert.setRoleId(Long.parseLong(role));
            sysRelationUserRoleMapper.insertSelective(insert);
        }

        return ComResponse.ok();
    }


    /////////////////////////

    /**
     * 按钮列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<SysButton>> listButton(SysButton bus, Pageable pageable) {
        if (null != pageable) {
            PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(), "sort_by desc");
        }
        SysButton record = new SysButton();
        if (StringUtils.isNotBlank(bus.getName())) {
            record.setName(bus.getName());
        }
        record.setIsDel(Constants.EXIST);
        if (null != pageable) {
            Page<SysButton> busList = (Page<SysButton>) sysButtonMapper.select(record);
            return ComResponse.ok(busList.getResult(), busList.getTotal());
        } else {
            return ComResponse.ok(sysButtonMapper.select(record));
        }
    }


    /**
     * 更新或者修改按钮
     *
     * @param bus
     * @param request
     * @return
     */
    public ComResponse<SysButton> insertOrUpdateButton(SysButton bus, HttpServletRequest request) {
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
                sysButtonMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateTime(new Date());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                sysButtonMapper.insertSelective(bus);
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
    public ComResponse<StringBuilder> deleteButton(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            SysButton delRecord = new SysButton();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = sysButtonMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

    /**
     * 更新或者修改菜单
     *
     * @param bus
     * @param buttons
     * @param request
     * @return
     */
    public ComResponse<SysRelationMenuButton> insertOrUpdateRelationMenuButton(SysRelationMenuButton bus, String buttons, HttpServletRequest request) {
        if (null == bus.getMenuId() || StringUtils.isBlank(buttons)) {
            return ComResponse.failBadRequest();
        }
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            //直接删除全部
            SysRelationMenuButton delRecord = new SysRelationMenuButton();
            delRecord.setMenuId(bus.getMenuId());
            sysRelationMenuButtonMapper.delete(delRecord);
            //直接添加新的
            String[] buttonAry = buttons.split(Constants.COMMA);
            for (String buttonId : buttonAry) {
                SysRelationMenuButton insert = new SysRelationMenuButton();
                insert.setId(IDKeyUtil.generateId());
                insert.setMenuId(bus.getMenuId());
                insert.setButtonId(Long.parseLong(buttonId));
                sysRelationMenuButtonMapper.insertSelective(insert);
            }
            return ComResponse.ok();
        } else {
            return ComResponse.failUnauthorized();
        }
    }
}
