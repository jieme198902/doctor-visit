package com.doctor.visit.service;

import com.doctor.visit.domain.BusGoodsSpecification;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface GoodsSpecificationService {
    /**
     * 前台 - 获取商品规格列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusGoodsSpecification>> listGoodsSpecification(BusGoodsSpecification bus, Pageable pageable);

    /**
     * 新增或者更新商品规格
     *
     * @param bus
     * @param request
     * @return
     */
    ComResponse<BusGoodsSpecification> insertOrUpdateGoodsSpecification(BusGoodsSpecification bus, HttpServletRequest request);

    /**
     * 根据id删除商品规格
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteGoodsSpecification(String ids);
}
