package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;

import com.doctor.visit.domain.BusEvaluate;
import com.doctor.visit.domain.BusGoods;
import com.doctor.visit.domain.BusGoodsClass;
import com.doctor.visit.service.EvaluateService;
import com.doctor.visit.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 商品接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@RestController
@Api("商品接口")
@RequestMapping(Constants.API_BASE_FRONT + "/goods")
public class FrontGoodsResource {

    private final GoodsService goodsService;
    private final EvaluateService evaluateService;


    public FrontGoodsResource(GoodsService goodsService, EvaluateService evaluateService) {
        this.goodsService = goodsService;
        this.evaluateService = evaluateService;
    }

    /**
     * 查询商品分类列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusGoodsClass.class)
    })
    @PostMapping("listGoodsClass")
    @ApiOperation(value = "查询商品分类列表")
    public Object listGoodsClass(BusGoodsClass bus, Pageable pageable) {
        return goodsService.listGoodsClass(bus, pageable);
    }

    /**
     * 查询商品分类列表无分页
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusGoodsClass.class)
    })
    @PostMapping("listGoodsClassNoPage")
    @ApiOperation(value = "查询商品分类列表无分页")
    public Object listGoodsClassNoPage(BusGoodsClass bus) {
        return goodsService.listGoodsClass(bus, null);
    }


    /**
     * 获取商品列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusGoods.class),
        @ApiImplicitParam(name = "classId", value = "分类id"),
        @ApiImplicitParam(name = "name", value = "商品名称"),
    })
    @PostMapping("listGoods")
    @ApiOperation(value = "获取商品列表")
    public Object getGoodsList(BusGoods bus, Pageable pageable) {
        return goodsService.listGoods(bus, pageable);
    }



}
