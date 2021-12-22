package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusOrderInquiry;
import com.doctor.visit.domain.param.UnifiedOrderParam;
import com.doctor.visit.service.OrderInquiryService;
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
    private final OrderInquiryService orderInquiryService;

    public FrontOrderInquiryResource(OrderInquiryService orderInquiryService) {
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
        bus.setCreateBy(Utils.getUserIdWithoutException(request));
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


    /**
     * 统一下单:商户在小程序中先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易后调起支付。
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     *
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = UnifiedOrderParam.class),
        @ApiImplicitParam(name = "out_trade_no", value = "订单号"),
        @ApiImplicitParam(name = "product_id", value = "产品id"),
        @ApiImplicitParam(name = "openid", value = "用户openid"),

        @ApiImplicitParam(name = "body", value = "支付描述"),
        @ApiImplicitParam(name = "total_fee", value = "支付钱数"),

    })
    @PostMapping("unifiedOrder")
    @ApiOperation(value = "问诊统一下单:商户在小程序中先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易后调起支付。")
    public Object unifiedOrder(UnifiedOrderParam param, HttpServletRequest request) throws Exception {
        return orderInquiryService.unifiedOrder(param, request);
    }

    /**
     * 更新订单，支付，回调
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("updateOrderStateForPay")
    @ApiOperation(value = "问诊前端不用：微信异步通知，更新订单，支付，回调")
    public Object updateOrderStateForPay(HttpServletRequest request) throws Exception {
        return orderInquiryService.updateOrderStateForPay(request);
    }





}
