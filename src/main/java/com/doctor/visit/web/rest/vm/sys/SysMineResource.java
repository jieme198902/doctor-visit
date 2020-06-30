package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusEvaluate;
import com.doctor.visit.service.MineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 我的的接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@Api("我的的接口")
@RestController
@RequestMapping(Constants.API_BASE_SYS + "/mine")
public class SysMineResource {
    private final MineService mineService;

    public SysMineResource(MineService mineService) {
        this.mineService = mineService;
    }

    /**
     * 后台 - 获取评价列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusEvaluate.class)
    })
    @PostMapping("listEvaluate")
    @ApiOperation(value = "后台 - 获取评价列表")
    public Object listEvaluate(BusEvaluate bus, Pageable pageable) {
        return mineService.listEvaluate(bus, pageable);
    }
}
