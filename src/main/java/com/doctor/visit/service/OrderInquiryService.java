package com.doctor.visit.service;

import com.doctor.visit.domain.BusOrderInquiry;
import com.doctor.visit.domain.dto.BusOrderInquiryDto;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderInquiryService {
    /**
     * 获取问诊订单列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusOrderInquiryDto>> listOrderInquiry(BusOrderInquiry bus, Pageable pageable);

    /**
     * 更新问诊订单的状态
     * 订单状态：0已提交，待支付；1已支付，待接诊；2已支付，已接诊；4已评价；5已取消
     *
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    ComResponse<BusOrderInquiry> insertOrUpdateOrderInquiryOrderState(BusOrderInquiry bus, HttpServletRequest request) throws Exception;

    /**
     * 前端 - 新增或者更新问诊订单
     *
     * @param bus
     * @param request 这里需要处理文件
     * @return
     */
    ComResponse<BusOrderInquiryDto> insertOrUpdateOrderInquiry(BusOrderInquiry bus, HttpServletRequest request) throws Exception;

    /**
     * 根据id删除问诊订单
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteOrderInquiry(String ids);
}
