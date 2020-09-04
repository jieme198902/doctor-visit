package com.doctor.visit.service;

import com.doctor.visit.domain.BusUserShippingAddress;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserShippingAddressService {
    /**
     * 前台 - 获取用户地址列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusUserShippingAddress>> listUserShippingAddress(BusUserShippingAddress bus, Pageable pageable, HttpServletRequest request) throws Exception;

    /**
     * 前台 - 新增或者更新用户地址
     *
     * @param bus
     * @param request
     * @return
     */
    ComResponse<BusUserShippingAddress> insertOrUpdateUserShippingAddress(BusUserShippingAddress bus, HttpServletRequest request) throws Exception;

    /**
     * 前台 - 根据id删除用户地址
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteUserShippingAddress(String ids);
}
