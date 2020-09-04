package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusOrderInquiry;
import com.doctor.visit.service.impl.OrderInquiryServiceImpl;
import com.doctor.visit.web.rest.util.Utils;
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
 * 微信 下问诊订单
 */
@RestController
@Api("微信 下问诊订单")
@RequestMapping(Constants.API_BASE_FRONT + "/order/inquiry")
public class FrontOrderInquiryResource {
    private final OrderInquiryServiceImpl orderInquiryService;

    public FrontOrderInquiryResource(OrderInquiryServiceImpl orderInquiryService) {
        this.orderInquiryService = orderInquiryService;
    }

    /**
     * 前端 - 获取问诊订单列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusOrderInquiry.class)
    })
    @PostMapping("listOrderInquiry")
    @ApiOperation(value = "前端 - 获取问诊订单列表")
    public Object listOrderInquiry(BusOrderInquiry bus, Pageable pageable, HttpServletRequest request) throws Exception {
        bus.setCreateBy(Utils.getUserId(request));
        return orderInquiryService.listOrderInquiry(bus, pageable);
    }


    /**
     * 新增或者更新问诊订单
     *
     * @param bus
     * @param request
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusOrderInquiry.class)
    })
    @PostMapping("insertOrUpdateOrderInquiry")
    @ApiOperation(value = "新增或者更新问诊订单")
    public Object insertOrUpdateOrderInquiry(BusOrderInquiry bus, HttpServletRequest request) throws Exception {
        return orderInquiryService.insertOrUpdateOrderInquiry(bus, request);
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
