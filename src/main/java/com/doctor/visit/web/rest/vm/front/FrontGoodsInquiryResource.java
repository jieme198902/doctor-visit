package com.doctor.visit.web.rest.vm.front;

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


/**
 * 商品接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@RestController
@Api("问诊商品接口")
@RequestMapping(Constants.API_BASE_FRONT + "/goodsInquiry")
public class FrontGoodsInquiryResource {

    private final GoodsInquiryService goodsInquiryService;

    public FrontGoodsInquiryResource(GoodsInquiryService goodsInquiryService) {
        this.goodsInquiryService = goodsInquiryService;
    }



    /**
     * 获取商品列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusGoodsInquiry.class),
        @ApiImplicitParam(name = "doctorName", value = "医生名称"),
        @ApiImplicitParam(name = "doctorId", value = "医生id"),
        @ApiImplicitParam(name = "askType", value = "问诊方式：0电话，1图文，2视频咨询"),
    })
    @PostMapping("listGoodsInquiry")
    @ApiOperation(value = "获取问诊商品列表")
    public Object listGoodsInquiry(BusGoodsInquiry bus, Pageable pageable) {
        return goodsInquiryService.listGoodsInquiry(bus, pageable);
    }



}
