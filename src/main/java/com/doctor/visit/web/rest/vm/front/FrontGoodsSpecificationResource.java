package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
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

/**
 * 商品规格的接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@Api("商品规格的接口")
@RestController
@RequestMapping(Constants.API_BASE_FRONT + "/goods/specification")
public class FrontGoodsSpecificationResource {

    private final GoodsSpecificationService goodsSpecificationService;

    public FrontGoodsSpecificationResource(GoodsSpecificationService goodsSpecificationService) {
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
}
