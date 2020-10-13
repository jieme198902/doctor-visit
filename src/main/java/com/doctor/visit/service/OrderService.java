package com.doctor.visit.service;


import com.doctor.visit.domain.BusOrderGoods;
import com.doctor.visit.domain.BusOrderGoodsTotal;
import com.doctor.visit.domain.dto.BusOrderGoodsTotalDto;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    /**
     * 前台 - 单商品下单
     * @param bus
     * @param request
     * @return
     */
    ComResponse<BusOrderGoodsTotalDto> insertOrder(BusOrderGoods bus, HttpServletRequest request) throws Exception;

    /**
     * 前台 - 购物车下单
     * @param userShoppingCart
     * @param request
     * @return
     */
    ComResponse<BusOrderGoodsTotalDto> insertOrderWithShoppingCart(String userShoppingCart, HttpServletRequest request) throws Exception;

    /**
     * 更新订单，支付，回调
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    Object updateOrderStateForPay(BusOrderGoodsTotal bus,HttpServletRequest request)throws Exception;

    /**
     * 获取用户的商品订单
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    ComResponse<List<BusOrderGoodsTotalDto>> listOrder(BusOrderGoodsTotal bus, Pageable pageable, HttpServletRequest request)throws Exception;
}
