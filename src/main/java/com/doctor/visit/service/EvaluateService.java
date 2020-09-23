package com.doctor.visit.service;

import com.doctor.visit.domain.BusEvaluate;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EvaluateService {
    /**
     * 后台 - 获取评价列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusEvaluate>> listEvaluate(BusEvaluate bus, Pageable pageable);

    /**
     * 根据商品id获取商品评论列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusEvaluate>> listEvaluateByGoodsId(BusEvaluate bus, Pageable pageable);

    /**
     * 后台 - 删除评价列表
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteEvaluate(String ids);
}
