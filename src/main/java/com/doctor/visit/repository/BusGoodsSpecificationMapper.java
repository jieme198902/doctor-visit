package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.BusGoodsSpecification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusGoodsSpecificationMapper extends CommMapper<BusGoodsSpecification> {

    /**
     *
     * @param goodsId
     * @param goodsName
     * @return
     */
    List<BusGoodsSpecification> selectByGoods(@Param("goodsId")Long goodsId,@Param("goodsName")String goodsName);

}
