package com.doctor.visit.service;

import com.doctor.visit.domain.BusGoodsInquiry;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface GoodsInquiryService {
    /**
     * 获取问诊商品列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusGoodsInquiry>> listGoodsInquiry(BusGoodsInquiry bus, Pageable pageable);

    /**
     * 新增或者更新问诊商品
     *
     * @param bus
     * @param request
     * @return
     */
    ComResponse<BusGoodsInquiry> insertOrUpdateGoodsInquiry(BusGoodsInquiry bus, HttpServletRequest request);

    /**
     * 根据id删除问诊商品
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteGoodsInquiry(String ids);
}
