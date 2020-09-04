package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusLog;
import com.doctor.visit.service.impl.SysLogServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取字典
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@RestController
@Api("日志接口")
@RequestMapping(Constants.API_BASE_SYS + "/log")
public class SysLogResource {

    private final SysLogServiceImpl sysLogService;

    public SysLogResource(SysLogServiceImpl sysLogService) {
        this.sysLogService = sysLogService;
    }


    /**
     * 查询日志列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusLog.class)
    })
    @PostMapping("listLog")
    @ApiOperation(value = "查询地区列表")
    public Object listLog(BusLog bus, Pageable pageable) {
        return sysLogService.listLog(bus, pageable);
    }


}
