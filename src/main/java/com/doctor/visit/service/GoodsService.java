package com.doctor.visit.service;

import com.doctor.visit.domain.BusGoods;
import com.doctor.visit.domain.BusGoodsClass;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

public interface GoodsService {
    /**
     * 后台 - 查询商品分类列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse listGoodsClass(BusGoodsClass bus, Pageable pageable);

    /**
     * 后台 - 新增或者更新商品分类
     *
     * @param bus
     * @return
     */
    ComResponse<BusGoodsClass> insertOrUpdateGoodsClass(BusGoodsClass bus);

    /**
     * 根据id删除商品分类
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteGoodsClass(String ids);

    /**
     * 后台 - 查询商品列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse listGoods(BusGoods bus, Pageable pageable);

    /**
     * 新增或者更新商品
     * 静态化商品生成url
     *
     * @param bus
     * @return
     */
    ComResponse<BusGoods> insertOrUpdateGoods(BusGoods bus);

    /**
     * 根据id删除商品
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteGoods(String ids);
}
