package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusDoctor;
import com.doctor.visit.domain.BusGoodsSpecification;
import com.doctor.visit.service.GoodsSpecificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 商品规格的接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@Api("商品规格的接口")
@RestController
@RequestMapping(Constants.API_BASE_SYS + "/goods/specification")
public class SysGoodsSpecificationResource {

    private final GoodsSpecificationService goodsSpecificationService;

    public SysGoodsSpecificationResource(GoodsSpecificationService goodsSpecificationService) {
        this.goodsSpecificationService = goodsSpecificationService;
    }


    /**
     * 获取商品规格列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusGoodsSpecification.class)
    })
    @PostMapping("listGoodsSpecification")
    @ApiOperation(value = "获取商品规格列表")
    public Object listGoodsSpecification(BusGoodsSpecification bus, Pageable pageable) {
        return goodsSpecificationService.listGoodsSpecification(bus, pageable);
    }


    /**
     * 新增或者更新商品规格
     *
     * @param bus
     * @param request
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusGoodsSpecification.class)
    })
    @PostMapping("insertOrUpdateGoodsSpecification")
    @ApiOperation(value = "新增或者更新商品规格")
    public Object insertOrUpdateGoodsSpecification(BusGoodsSpecification bus, HttpServletRequest request) {
        return goodsSpecificationService.insertOrUpdateGoodsSpecification(bus, request);
    }

    /**
     * 根据id删除商品规格
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusGoodsSpecification.class)
    })
    @PostMapping("deleteGoodsSpecification")
    @ApiOperation(value = "根据id删除商品规格")
    public Object deleteGoodsSpecification(String ids) {
        return goodsSpecificationService.deleteGoodsSpecification(ids);
    }
}
