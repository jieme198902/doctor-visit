package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusSelfDiagnose;
import com.doctor.visit.domain.BusSelfDiagnosis;
import com.doctor.visit.service.impl.SelfDiagnosticsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 微信自诊的接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@Api("微信自诊的接口")
@RestController
@RequestMapping(Constants.API_BASE_FRONT + "/self/diagnostics")
public class FrontSelfDiagnosticsResource {
    private final SelfDiagnosticsServiceImpl selfDiagnosticsService;

    public FrontSelfDiagnosticsResource(SelfDiagnosticsServiceImpl selfDiagnosticsService) {
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
        @ApiImplicitParam(dataTypeClass = BusSelfDiagnose.class),
        @ApiImplicitParam(name = "id",value = "id,获取该id下的所有子项，顶级项传0"),

    })
    @PostMapping("listSelfDiagnose")
    @ApiOperation(value = "获取自诊问题列表")
    public Object listSelfDiagnose(BusSelfDiagnose bus, Pageable pageable) {
        return selfDiagnosticsService.listSelfDiagnose(bus, pageable);
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
}
