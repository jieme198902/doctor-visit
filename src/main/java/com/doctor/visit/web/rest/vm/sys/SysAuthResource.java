package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.service.SysAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 权鉴接口
 */
@RestController
@Api("权鉴接口")
@RequestMapping(Constants.API_BASE_SYS + "/auth")
public class SysAuthResource {
    private final SysAuthService sysAuthService;

    public SysAuthResource(SysAuthService sysAuthService) {
        this.sysAuthService = sysAuthService;
    }

    /**
     * 查询角色列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysRole.class)
    })
    @PostMapping("listRole")
    @ApiOperation(value = "查询角色列表")
    public Object listRole(SysRole bus, Pageable pageable) {
        return sysAuthService.listRole(bus, pageable);
    }

    /**
     * 查询无分页的角色列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysRole.class)
    })
    @PostMapping("listRoleNoPage")
    @ApiOperation(value = "查询无分页的角色列表")
    public Object listRoleNoPage(SysRole bus) {
        return sysAuthService.listRole(bus, null);
    }


    /**
     * 新增或者修改角色
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysRole.class)
    })
    @PostMapping("insertOrUpdateRole")
    @ApiOperation(value = "新增或者修改角色")
    public Object insertOrUpdateRole(SysRole bus, HttpServletRequest request) {
        return sysAuthService.insertOrUpdateRole(bus, request);
    }

    /**
     * 根据id删除角色
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("deleteRole")
    @ApiOperation(value = "根据id删除角色")
    public Object deleteRole(String ids) {
        return sysAuthService.deleteRole(ids);
    }


    //////////////////////////////

    /**
     * 查询菜单列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysMenu.class)
    })
    @PostMapping("listMenu")
    @ApiOperation(value = "查询菜单列表")
    public Object listMenu(SysMenu bus, Pageable pageable) {
        return sysAuthService.listMenu(bus, pageable);
    }

    /**
     * 获取菜单树
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysMenu.class)
    })
    @PostMapping("listMenuTree")
    @ApiOperation(value = "获取菜单树")
    public Object listMenuTree(SysMenu bus) {
        return sysAuthService.listMenuTree(bus);
    }

    /**
     * 获取菜单树根据角色id
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysMenu.class)
    })
    @PostMapping("listMenuByRoleId")
    @ApiOperation(value = "获取菜单树根据角色id")
    public Object listMenuByRoleId(SysPermission bus) {
        return sysAuthService.listMenuByRoleId(bus);
    }


    /**
     * 查询无分页的菜单列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysMenu.class)
    })
    @PostMapping("listMenuNoPage")
    @ApiOperation(value = "查询无分页的菜单列表")
    public Object listMenuNoPage(SysMenu bus) {
        return sysAuthService.listMenu(bus, null);
    }


    /**
     * 新增或者修改菜单
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysMenu.class)
    })
    @PostMapping("insertOrUpdateMenu")
    @ApiOperation(value = "新增或者修改菜单")
    public Object insertOrUpdateMenu(SysMenu bus, HttpServletRequest request) {
        return sysAuthService.insertOrUpdateMenu(bus, request);
    }

    /**
     * 根据id删除菜单
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("deleteMenu")
    @ApiOperation(value = "根据id删除菜单")
    public Object deleteMenu(String ids) {
        return sysAuthService.deleteMenu(ids);
    }


    /**
     * 根据菜单获取按钮列表
     *
     * @param sysMenu
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("selectByMenu")
    @ApiOperation(value = "根据菜单获取按钮列表")
    public Object selectByMenu(SysMenu sysMenu) {
        return sysAuthService.selectByMenu(sysMenu);
    }
    /////////////////////

    /**
     * 修改权限
     *
     * @param bus
     * @param menus
     * @param request
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysPermission.class)
    })
    @ApiOperation(value = "修改权限")
    @PostMapping("insertOrUpdatePermission")
    public Object insertOrUpdatePermission(SysPermission bus, String menus, HttpServletRequest request) {
        return sysAuthService.insertOrUpdatePermission(bus, menus, request);
    }


    /**
     * 修改用户的角色
     *
     * @param bus
     * @param roles
     * @param request
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysRelationUserRole.class)
    })
    @ApiOperation(value = "修改用户的角色")
    @PostMapping("insertOrUpdateUserRole")
    public Object insertOrUpdateUserRole(SysRelationUserRole bus, String roles, HttpServletRequest request) {
        return sysAuthService.insertOrUpdateUserRole(bus, roles, request);
    }

    //////////////////

    /**
     * 查询按钮列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysButton.class)
    })
    @PostMapping("listButton")
    @ApiOperation(value = "查询按钮列表")
    public Object listButton(SysButton bus, Pageable pageable) {
        return sysAuthService.listButton(bus, pageable);
    }

    /**
     * 查询无分页的按钮列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysButton.class)
    })
    @PostMapping("listButtonNoPage")
    @ApiOperation(value = "查询无分页的按钮列表")
    public Object listButtonNoPage(SysButton bus) {
        return sysAuthService.listButton(bus, null);
    }


    /**
     * 新增或者修改按钮
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysButton.class)
    })
    @PostMapping("insertOrUpdateButton")
    @ApiOperation(value = "新增或者修改按钮")
    public Object insertOrUpdateButton(SysButton bus, HttpServletRequest request) {
        return sysAuthService.insertOrUpdateButton(bus, request);
    }

    /**
     * 根据id删除按钮
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("deleteButton")
    @ApiOperation(value = "根据id删除按钮")
    public Object deleteButton(String ids) {
        return sysAuthService.deleteButton(ids);
    }


    /**
     * 修改 菜单-按钮 关系表
     *
     * @param bus
     * @param buttons
     * @param request
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysRelationMenuButton.class)
    })
    @ApiOperation(value = "修改 菜单-按钮 关系表")
    @PostMapping("insertOrUpdateRelationMenuButton")
    public Object insertOrUpdateRelationMenuButton(SysRelationMenuButton bus, String buttons, HttpServletRequest request) {
        return sysAuthService.insertOrUpdateRelationMenuButton(bus, buttons, request);
    }




}
