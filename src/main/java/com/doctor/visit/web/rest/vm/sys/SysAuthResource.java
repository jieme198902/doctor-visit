package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.SysMenu;
import com.doctor.visit.domain.SysRole;
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
        return sysAuthService.insertOrUpdateRole(bus,request);
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
        return sysAuthService.insertOrUpdateMenu(bus,request);
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

}
