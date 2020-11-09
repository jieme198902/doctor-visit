package com.doctor.visit.service;

import com.doctor.visit.domain.BusUserShoppingCart;
import com.doctor.visit.domain.dto.BusUserShoppingCartDto;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserShoppingCartService {
    /**
     * 前台 - 获取用户购物车列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusUserShoppingCartDto>> listUserShoppingCart(BusUserShoppingCart bus, Pageable pageable, HttpServletRequest request) throws Exception;
    /**
     * 前台 - 新增或者更新用户购物车
     *
     * @param bus
     * @param request
     * @return
     */
    ComResponse<BusUserShoppingCart> insertOrUpdateUserShoppingCart(BusUserShoppingCart bus, HttpServletRequest request) throws Exception;
    /**
     * 前台 - 根据id删除用户购物车
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteUserShoppingCart(String ids);
}
