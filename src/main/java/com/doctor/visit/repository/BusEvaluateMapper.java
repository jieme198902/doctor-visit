package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.BusEvaluate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusEvaluateMapper extends CommMapper<BusEvaluate> {


    /**
     *
     * @param goodsId
     * @return
     */
    List<BusEvaluate> selectEvaluateByGoodsId(@Param("goodsId") Long goodsId);
}
