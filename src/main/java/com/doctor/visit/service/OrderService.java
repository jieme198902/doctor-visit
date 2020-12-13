package com.doctor.visit.service;


import com.doctor.visit.domain.BusOrderGoods;
import com.doctor.visit.domain.BusOrderGoodsTotal;
import com.doctor.visit.domain.dto.BusOrderGoodsTotalDto;
import com.doctor.visit.domain.param.UnifiedOrderParam;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    /**
     * 前台 - 单商品下单
     *
     * @param bus
     * @param request
     * @return
     */
    ComResponse<BusOrderGoodsTotalDto> insertOrder(BusOrderGoods bus, HttpServletRequest request) throws Exception;

    /**
     * 前台 - 购物车下单
     *
     * @param userShoppingCart
     * @param request
     * @return
     */
    ComResponse<BusOrderGoodsTotalDto> insertOrderWithShoppingCart(String userShoppingCart, HttpServletRequest request) throws Exception;

    /**
     * 统一下单:商户在小程序中先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易后调起支付
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    Object unifiedOrder(UnifiedOrderParam param, HttpServletRequest request) throws Exception;

    /**
     * 更新订单，支付，回调
     *
     * @param request
     * @return
     * @throws Exception
     */
    Object updateOrderStateForPay(HttpServletRequest request) throws Exception;

    /**
     * 确认收货
     *
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    Object updateOrderReceiving(BusOrderGoodsTotal bus, HttpServletRequest request) throws Exception;

    /**
     * 取消订单
     *
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    Object updateOrderCancel(BusOrderGoodsTotal bus, HttpServletRequest request) throws Exception;

    /**
     * 删除订单
     *
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    Object updateOrderDelete(BusOrderGoodsTotal bus, HttpServletRequest request) throws Exception;

    /**
     * 获取用户的商品订单
     *
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    ComResponse<List<BusOrderGoodsTotalDto>> listOrder(BusOrderGoodsTotal bus, Pageable pageable, HttpServletRequest request) throws Exception;
}
