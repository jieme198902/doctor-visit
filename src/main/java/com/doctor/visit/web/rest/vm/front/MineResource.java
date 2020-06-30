package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusEvaluate;
import com.doctor.visit.service.MineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 前端 我的接口
 */
@RestController
@Api("微信我的的接口")
@RequestMapping(Constants.API_BASE_FRONT + "/mine")
public class MineResource {

    private final MineService mineService;

    public MineResource(MineService mineService) {
        this.mineService = mineService;
    }


    /**
     * 评价接口，评价医生、商品
     * <p>
     * module  模块：0医生，1商品
     *
     * @param busEvaluate
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusEvaluate.class)
    })
    @PostMapping("insertOrUpdateEvaluate")
    @ApiOperation(value = "评价接口，评价医生、商品 --> module  模块：0医生，1商品")
    public Object insertOrUpdateEvaluate(BusEvaluate busEvaluate, HttpServletRequest request) {
        return mineService.insertOrUpdateEvaluate(busEvaluate, request);
    }
}
