package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusSelfDiagnose;
import com.doctor.visit.domain.BusSelfDiagnosis;
import com.doctor.visit.service.SelfDiagnosticsService;
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
 * 自诊的接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@Api("自诊的接口")
@RestController
@RequestMapping(Constants.API_BASE_SYS + "/self/diagnostics")
public class SysSelfDiagnosticsResource {
    private final SelfDiagnosticsService selfDiagnosticsService;

    public SysSelfDiagnosticsResource(SelfDiagnosticsService selfDiagnosticsService) {
        this.selfDiagnosticsService = selfDiagnosticsService;
    }

    /**
     * 获取自诊问题列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusSelfDiagnose.class)
    })
    @PostMapping("listSelfDiagnose")
    @ApiOperation(value = "获取自诊问题列表")
    public Object listSelfDiagnose(BusSelfDiagnose bus, Pageable pageable) {
        return selfDiagnosticsService.listSelfDiagnose(bus, pageable);
    }


    /**
     * 新增或者更新自诊问题
     *
     * @param bus
     * @param request
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusSelfDiagnose.class)
    })
    @PostMapping("insertOrUpdateSelfDiagnose")
    @ApiOperation(value = "新增或者更新自诊问题")
    public Object insertOrUpdateSelfDiagnose(BusSelfDiagnose bus, HttpServletRequest request) {
        return selfDiagnosticsService.insertOrUpdateSelfDiagnose(bus, request);
    }

    /**
     * 根据id删除自诊问题
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusSelfDiagnose.class)
    })
    @PostMapping("deleteSelfDiagnose")
    @ApiOperation(value = "根据id删除自诊问题")
    public Object deleteSelfDiagnose(String ids) {
        return selfDiagnosticsService.deleteSelfDiagnose(ids);
    }

    /**
     * 获取自诊结果列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusSelfDiagnosis.class)
    })
    @PostMapping("listGSelfDiagnosis")
    @ApiOperation(value = "获取自诊结果列表")
    public Object listGSelfDiagnosis(BusSelfDiagnosis bus, Pageable pageable) {
        return selfDiagnosticsService.listSelfDiagnosis(bus, pageable);
    }


    /**
     * 新增或者更新自诊结果
     *
     * @param bus
     * @param request
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusSelfDiagnosis.class)
    })
    @PostMapping("insertOrUpdateSelfDiagnosis")
    @ApiOperation(value = "新增或者更新自诊结果")
    public Object insertOrUpdateSelfDiagnosis(BusSelfDiagnosis bus, HttpServletRequest request) {
        return selfDiagnosticsService.insertOrUpdateSelfDiagnosis(bus, request);
    }

    /**
     * 根据id删除自诊结果
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusSelfDiagnosis.class)
    })
    @PostMapping("deleteSelfDiagnosis")
    @ApiOperation(value = "根据id删除自诊结果")
    public Object deleteSelfDiagnosis(String ids) {
        return selfDiagnosticsService.deleteSelfDiagnosis(ids);
    }
}
