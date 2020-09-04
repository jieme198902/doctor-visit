package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusGoodsInquiry;
import com.doctor.visit.service.GoodsInquiryService;
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
 * 问诊商品的接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@Api("问诊商品的接口")
@RestController
@RequestMapping(Constants.API_BASE_SYS + "/goods/inquiry")
public class SysGoodsInquiryResource {

    private final GoodsInquiryService goodsInquiryService;

    public SysGoodsInquiryResource(GoodsInquiryService goodsInquiryService) {
        this.goodsInquiryService = goodsInquiryService;
    }


    /**
     * 获取商品规格列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusGoodsInquiry.class)
    })
    @PostMapping("listGoodsInquiry")
    @ApiOperation(value = "获取商品规格列表")
    public Object listGoodsInquiry(BusGoodsInquiry bus, Pageable pageable) {
        return goodsInquiryService.listGoodsInquiry(bus, pageable);
    }


    /**
     * 新增或者更新商品规格
     *
     * @param bus
     * @param request
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusGoodsInquiry.class)
    })
    @PostMapping("insertOrUpdateGoodsInquiry")
    @ApiOperation(value = "新增或者更新商品规格")
    public Object insertOrUpdateGoodsInquiry(BusGoodsInquiry bus, HttpServletRequest request) {
        return goodsInquiryService.insertOrUpdateGoodsInquiry(bus, request);
    }

    /**
     * 根据id删除商品规格
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusGoodsInquiry.class)
    })
    @PostMapping("deleteGoodsInquiry")
    @ApiOperation(value = "根据id删除商品规格")
    public Object deleteGoodsInquiry(String ids) {
        return goodsInquiryService.deleteGoodsInquiry(ids);
    }
}
