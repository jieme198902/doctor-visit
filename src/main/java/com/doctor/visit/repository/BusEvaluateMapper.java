package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.BusEvaluate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusEvaluateMapper extends CommMapper<BusEvaluate> {


    /**
     *
     * @param goodsId
     * @return
     */
    List<BusEvaluate> selectEvaluateByGoodsId(@Param("goodsId") Long goodsId);
}
