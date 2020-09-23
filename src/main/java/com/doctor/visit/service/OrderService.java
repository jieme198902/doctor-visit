package com.doctor.visit.service;


import com.doctor.visit.domain.BusOrderGoods;
import com.doctor.visit.domain.dto.BusOrderGoodsTotalDto;
import com.doctor.visit.web.rest.util.ComResponse;

import javax.servlet.http.HttpServletRequest;

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
}
