package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.service.MineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
     * 后台 - 首页：微信总用户数，微信本月新增用户，
     *
     * @return
     */
    @ApiImplicitParams({
//        @ApiImplicitParam(dataTypeClass = BusEvaluate.class)
    })
    @PostMapping("findStatisticsUser")
    @ApiOperation(value = "后台 - 首页：微信总用户数，微信本月新增用户")
    public Object findStatisticsUser() {
        return mineService.findStatisticsUser();
    }

    /**
     * 后台 - 首页：获取最近登录状态
     *
     * @return
     */
    @ApiImplicitParams({
//        @ApiImplicitParam(dataTypeClass = BusEvaluate.class)
    })
    @PostMapping("findLastLoginInfo")
    @ApiOperation(value = "后台 - 首页 - 获取最近登录状态")
    public Object findLastLoginInfo() {
        return mineService.findLastLoginInfo();
    }

}
