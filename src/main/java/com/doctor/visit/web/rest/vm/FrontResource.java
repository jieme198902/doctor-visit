package com.doctor.visit.web.rest.vm;


import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusFeedback;
import com.doctor.visit.domain.BusFile;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.domain.WkFxtInsulinFee;
import com.doctor.visit.service.FeedbackService;
import com.doctor.visit.service.FrontService;
import com.doctor.visit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 我的接口
 */
@Ignore
@RestController
@Api("胰岛素泵费用")
@RequestMapping(Constants.API_BASE_FRONT + "/f")
public class FrontResource {

    private final FrontService frontService;

    public FrontResource(FrontService frontService) {
        this.frontService = frontService;
    }

    /**
     * 插入或者更新个人费用
     * @param bus

     * @param request
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
    })
    @PostMapping("insertOrUpdateInsulinFee")
    @ApiOperation(value = "插入或者更新个人费用")
    public Object insertOrUpdateInsulinFee(WkFxtInsulinFee bus, HttpServletRequest request) throws Exception {
        return frontService.insertOrUpdateInsulinFee(bus, request);
    }

    /**
     * 获取月份的消费情况
     * @param bus

     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
    })
    @PostMapping("selectInsulinFeeByMonth")
    @ApiOperation(value = "获取月份的消费情况")
    public Object selectInsulinFeeByMonth(WkFxtInsulinFee bus) throws Exception {
        return frontService.selectInsulinFeeByMonth(bus);
    }

    /**
     * 生成表格
     * @param bus

     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
    })
    @PostMapping("generateExcel")
    @ApiOperation(value = "获取月份的消费情况")
    public Object generateExcel(WkFxtInsulinFee bus,String email) throws Exception {
        return frontService.generateExcel(bus,email);
    }

}
