package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusOrderInquiry;
import com.doctor.visit.service.OrderInquiryService;
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
 * 问诊订单的接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@Api("问诊订单的接口")
@RestController
@RequestMapping(Constants.API_BASE_SYS + "/order/inquiry")
public class SysOrderInquiryResource {

    private final OrderInquiryService orderInquiryService;

    public SysOrderInquiryResource(OrderInquiryService orderInquiryService) {
        this.orderInquiryService = orderInquiryService;
    }


    /**
     * 获取问诊订单列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusOrderInquiry.class)
    })
    @PostMapping("listOrderInquiry")
    @ApiOperation(value = "获取问诊订单列表")
    public Object listOrderInquiry(BusOrderInquiry bus, Pageable pageable) {
        return orderInquiryService.listOrderInquiry(bus, pageable);
    }


    /**
     * 更新问诊订单状态
     *
     * @param bus
     * @param request
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusOrderInquiry.class)
    })
    @PostMapping("insertOrUpdateOrderInquiryOrderState")
    @ApiOperation(value = "更新问诊订单状态")
    public Object insertOrUpdateOrderInquiryOrderState(BusOrderInquiry bus, HttpServletRequest request) throws Exception {
        return orderInquiryService.insertOrUpdateOrderInquiryOrderState(bus, request);
    }

    /**
     * 根据id删除问诊订单
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusOrderInquiry.class)
    })
    @PostMapping("deleteOrderInquiry")
    @ApiOperation(value = "根据id删除问诊订单")
    public Object deleteOrderInquiry(String ids) {
        return orderInquiryService.deleteOrderInquiry(ids);
    }
}
