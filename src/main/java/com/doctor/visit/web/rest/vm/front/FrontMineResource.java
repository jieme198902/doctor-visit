package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusEvaluate;
import com.doctor.visit.domain.BusPatient;
import com.doctor.visit.domain.BusUserShippingAddress;
import com.doctor.visit.domain.BusUserShoppingCart;
import com.doctor.visit.service.*;
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
 * 前端 微信我的接口
 */
@RestController
@Api("微信我的的接口")
@RequestMapping(Constants.API_BASE_FRONT + "/mine")
public class FrontMineResource {

    private final MineService mineService;
    private final PatientService patientService;
    private final UserShippingAddressService userShippingAddressService;
    private final UserShoppingCartService userShoppingCartService;

    public FrontMineResource(MineService mineService, PatientService patientService, UserShippingAddressService userShippingAddressService, UserShoppingCartService userShoppingCartService) {
        this.mineService = mineService;
        this.patientService = patientService;
        this.userShippingAddressService = userShippingAddressService;
        this.userShoppingCartService = userShoppingCartService;
    }


    /**
     * 评价接口，评价医生、商品
     * <p>
     * module  模块：0医生，1商品
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusEvaluate.class)
    })
    @PostMapping("insertOrUpdateEvaluate")
    @ApiOperation(value = "评价接口，评价医生、商品 --> module  模块：0医生，1商品")
    public Object insertOrUpdateEvaluate(BusEvaluate bus, HttpServletRequest request) throws Exception {
        return mineService.insertOrUpdateEvaluate(bus, request);
    }

    /**
     * 根据id删除评价
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusEvaluate.class)
    })
    @PostMapping("deleteEvaluate")
    @ApiOperation(value = "根据id删除评价")
    public Object deleteEvaluate(String ids) {
        return mineService.deleteEvaluate(ids);
    }


    /**
     * 前台 - 获取患者列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusPatient.class)
    })
    @PostMapping("listPatient")
    @ApiOperation(value = "前台 - 获取患者列表")
    public Object listPatient(BusPatient bus, Pageable pageable,HttpServletRequest request) throws Exception {
        return patientService.listPatient(bus, pageable,request,false);
    }


    /**
     * 前台 - 新增或者更新患者
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusPatient.class)
    })
    @PostMapping("insertOrUpdatePatient")
    @ApiOperation(value = "前台 - 新增或者更新患者")
    public Object insertOrUpdatePatient(BusPatient bus, HttpServletRequest request) {
        return patientService.insertOrUpdatePatient(bus, request);
    }


    /**
     * 前台 - 根据id删除患者
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusEvaluate.class)
    })
    @PostMapping("deletePatient")
    @ApiOperation(value = "前台 - 根据id删除患者")
    public Object deletePatient(String ids) {
        return patientService.deletePatient(ids);
    }


    /**
     * 前台 - 获取收货地址列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusUserShippingAddress.class)
    })
    @PostMapping("listUserShippingAddress")
    @ApiOperation(value = "前台 - 获取收货地址列表")
    public Object listUserShippingAddress(BusUserShippingAddress bus, Pageable pageable) {
        return userShippingAddressService.listUserShippingAddress(bus, pageable);
    }


    /**
     * 前台 - 新增或者更新收货地址
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusUserShippingAddress.class)
    })
    @PostMapping("insertOrUpdateUserShippingAddress")
    @ApiOperation(value = "前台 - 新增或者更新收货地址")
    public Object insertOrUpdateUserShippingAddress(BusUserShippingAddress bus, HttpServletRequest request) {
        return userShippingAddressService.insertOrUpdateUserShippingAddress(bus, request);
    }


    /**
     * 前台 - 根据id删除收货地址
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusUserShippingAddress.class)
    })
    @PostMapping("deleteUserShippingAddress")
    @ApiOperation(value = "前台 - 根据id删除收货地址")
    public Object deleteUserShippingAddress(String ids) {
        return userShippingAddressService.deleteUserShippingAddress(ids);
    }


    /**
     * 前台 - 获取购物车的商品列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusUserShoppingCart.class)
    })
    @PostMapping("listUserShippingCart")
    @ApiOperation(value = "前台 - 获取购物车的商品列表")
    public Object listUserShippingCart(BusUserShoppingCart bus, Pageable pageable) {
        return userShoppingCartService.listUserShoppingCart(bus, pageable);
    }


    /**
     * 前台 - 新增或者更新购物车里的商品
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusUserShippingAddress.class)
    })
    @PostMapping("insertOrUpdateUserShoppingCart")
    @ApiOperation(value = "前台 - 新增或者更新购物车里的商品")
    public Object insertOrUpdateUserShippingAddress(BusUserShoppingCart bus, HttpServletRequest request) {
        return userShoppingCartService.insertOrUpdateUserShoppingCart(bus, request);
    }


    /**
     * 前台 - 根据id删除购物车里的商品
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusUserShippingAddress.class)
    })
    @PostMapping("deleteUserShoppingCart")
    @ApiOperation(value = "前台 - 根据id删除购物车里的商品")
    public Object deleteUserShoppingCart(String ids) {
        return userShoppingCartService.deleteUserShoppingCart(ids);
    }


}
