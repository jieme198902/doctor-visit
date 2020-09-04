package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.service.SysOrganizeService;
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
 * 组织架构接口
 */
@RestController
@Api("组织架构接口")
@RequestMapping(Constants.API_BASE_SYS + "/organize")
public class SysOrganizeResource {
    private final SysOrganizeService sysOrganizeService;

    public SysOrganizeResource(SysOrganizeService sysOrganizeService) {
        this.sysOrganizeService = sysOrganizeService;
    }


    /**
     * 查询部门列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysDept.class)
    })
    @PostMapping("listDept")
    @ApiOperation(value = "查询部门列表")
    public Object listDept(SysDept bus, Pageable pageable) {
        return sysOrganizeService.listDept(bus, pageable);
    }

    /**
     * 查询无分页的部门列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysDept.class)
    })
    @PostMapping("listDeptNoPage")
    @ApiOperation(value = "查询无分页的部门列表")
    public Object listDeptNoPage(SysDept bus) {
        return sysOrganizeService.listDept(bus, null);
    }


    /**
     * 新增或者修改部门
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysDept.class)
    })
    @PostMapping("insertOrUpdateDept")
    @ApiOperation(value = "新增或者修改部门")
    public Object insertOrUpdateDept(SysDept bus, HttpServletRequest request) {
        return sysOrganizeService.insertOrUpdateDept(bus, request);
    }

    /**
     * 根据id删除部门
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("deleteDept")
    @ApiOperation(value = "根据id删除部门")
    public Object deleteDept(String ids) {
        return sysOrganizeService.deleteDept(ids);
    }


    //////////////////////////////

    /**
     * 查询岗位列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysJob.class)
    })
    @PostMapping("listJob")
    @ApiOperation(value = "查询岗位列表")
    public Object listJob(SysJob bus, Pageable pageable) {
        return sysOrganizeService.listJob(bus, pageable);
    }


    /**
     * 查询无分页的岗位列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysJob.class)
    })
    @PostMapping("listJobNoPage")
    @ApiOperation(value = "查询无分页的岗位列表")
    public Object listJobNoPage(SysJob bus) {
        return sysOrganizeService.listJob(bus, null);
    }


    /**
     * 新增或者修改岗位
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysJob.class)
    })
    @PostMapping("insertOrUpdateJob")
    @ApiOperation(value = "新增或者修改岗位")
    public Object insertOrUpdateJob(SysJob bus, HttpServletRequest request) {
        return sysOrganizeService.insertOrUpdateJob(bus, request);
    }

    /**
     * 根据id删除岗位
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("deleteJob")
    @ApiOperation(value = "根据id删除岗位")
    public Object deleteJob(String ids) {
        return sysOrganizeService.deleteJob(ids);
    }

}
