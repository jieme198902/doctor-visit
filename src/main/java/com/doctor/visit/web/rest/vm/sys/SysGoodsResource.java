package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;

import com.doctor.visit.domain.BusEvaluate;
import com.doctor.visit.domain.BusGoods;
import com.doctor.visit.domain.BusGoodsClass;
import com.doctor.visit.domain.BusGoodsSpecification;
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
@RequestMapping(Constants.API_BASE_SYS + "/goods")
public class SysGoodsResource {

    private final GoodsService goodsService;
    private final EvaluateService evaluateService;


    public SysGoodsResource(GoodsService goodsService, EvaluateService evaluateService) {
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
     * 查询商品分类列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusGoodsClass.class)
    })
    @PostMapping("listGoodsClassNoPage")
    @ApiOperation(value = "查询商品分类列表")
    public Object listGoodsClassNoPage(BusGoodsClass bus) {
        return goodsService.listGoodsClass(bus, null);
    }

    /**
     * 新增或者修改商品分类
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusGoodsClass.class)
    })
    @PostMapping("insertOrUpdateGoodsClass")
    @ApiOperation(value = "新增或者修改商品分类")
    public Object insertOrUpdateGoodsClass(BusGoodsClass bus) {
        return goodsService.insertOrUpdateGoodsClass(bus);
    }

    /**
     * 根据id删除商品分类
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("deleteGoodsClass")
    @ApiOperation(value = "根据id删除商品分类")
    public Object deleteGoodsClass(String ids) {
        return goodsService.deleteGoodsClass(ids);
    }

    /**
     * 获取商品列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusGoods.class)
    })
    @PostMapping("listGoods")
    @ApiOperation(value = "获取商品列表")
    public Object getGoodsList(BusGoods bus, Pageable pageable) {
        return goodsService.listGoods(bus, pageable);
    }

    /**
     * 新增或者修改商品
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusGoods.class)
    })
    @PostMapping("insertOrUpdateGoods")
    @ApiOperation(value = "新增或者修改商品")
    public Object insertOrUpdateGoods(BusGoods bus) {
        return goodsService.insertOrUpdateGoods(bus);
    }

    /**
     * 根据id删除商品
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("deleteGoods")
    @ApiOperation(value = "根据id删除商品")
    public Object deleteGoods(String ids) {
        return goodsService.deleteGoods(ids);
    }



    /**
     * 商品规格列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusGoods.class)
    })
    @PostMapping("listGoodsSpecification")
    @ApiOperation(value = "商品规格列表")
    public Object listGoodsSpecification(BusGoodsSpecification bus,BusGoods busGoods, Pageable pageable) {
        return goodsService.listGoodsSpecification(bus, busGoods, pageable);
    }

    /**
     * 新增或者更新商品规格
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(name ="goodsId", value = "商品id"),
        @ApiImplicitParam(name ="name", value = "商品名称")
    })
    @PostMapping("insertOrUpdateGoodsSpecification")
    @ApiOperation(value = "新增或者更新商品规格")
    public Object insertOrUpdateGoodsSpecification(BusGoodsSpecification bus,BusGoods busGoods) {
        return goodsService.insertOrUpdateGoodsSpecification(bus,busGoods);
    }

    /**
     * 根据id删除商品规格
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("deleteGoodsSpecification")
    @ApiOperation(value = "根据id删除商品规格")
    public Object deleteGoodsSpecification(String ids) {
        return goodsService.deleteGoodsSpecification(ids);
    }


    /**
     * 后台 - 获取评价列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusEvaluate.class)
    })
    @PostMapping("listEvaluate")
    @ApiOperation(value = "后台 - 获取评价列表")
    public Object listEvaluate(BusEvaluate bus, Pageable pageable) {
        return evaluateService.listEvaluate(bus, pageable);
    }

    /**
     * 根据id删除评价
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("deleteEvaluate")
    @ApiOperation(value = "根据id删除评价")
    public Object deleteEvaluate(String ids) {
        return evaluateService.deleteEvaluate(ids);
    }
}
