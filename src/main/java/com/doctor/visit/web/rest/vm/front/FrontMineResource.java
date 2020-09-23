package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
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
    private final OrderService orderService;
    private final PatientService patientService;
    private final UserShippingAddressService userShippingAddressService;
    private final UserShoppingCartService userShoppingCartService;

    public FrontMineResource(MineService mineService, OrderService orderService, PatientService patientService, UserShippingAddressService userShippingAddressService, UserShoppingCartService userShoppingCartService) {
        this.mineService = mineService;
        this.orderService = orderService;
        this.patientService = patientService;
        this.userShippingAddressService = userShippingAddressService;
        this.userShoppingCartService = userShoppingCartService;
    }


    /**
     * 10.“我的”界面，需要一个返回 当前收藏数、分享数、关注数的接口
     * <p>
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusEvaluate.class),
        @ApiImplicitParam(name = "token", value = "header中的token"),

    })
    @PostMapping("findMineCount")
    @ApiOperation(value = "“我的”界面，需要一个返回 当前收藏数、分享数、关注数的接口")
    public Object findMineCount(BusUser bus, HttpServletRequest request) throws Exception {
        return mineService.findMineCount(bus, request);
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
        @ApiImplicitParam(dataTypeClass = BusEvaluate.class),
        @ApiImplicitParam(name = "token", value = "header中的token"),
        @ApiImplicitParam(name = "orderId", value = "订单id"),
        @ApiImplicitParam(name = "module", value = "模块：0问诊，1商品"),
        @ApiImplicitParam(name = "moduleId", value = "问诊订单id，商品订单id"),
        @ApiImplicitParam(name = "evaluate", value = "评价内容"),
        @ApiImplicitParam(name = "score", value = "评分0-5"),
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
        @ApiImplicitParam(dataTypeClass = BusEvaluate.class),
        @ApiImplicitParam(name = "token", value = "header中的token"),
        @ApiImplicitParam(name = "ids", value = "评价id，多个用,隔开"),
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
        @ApiImplicitParam(dataTypeClass = BusPatient.class),
        @ApiImplicitParam(name = "token", value = "header中的token"),
        @ApiImplicitParam(name = "name", value = "患者名称"),
    })
    @PostMapping("listPatient")
    @ApiOperation(value = "前台 - 获取患者列表")
    public Object listPatient(BusPatient bus, Pageable pageable, HttpServletRequest request) throws Exception {
        return patientService.listPatient(bus, pageable, request, false);
    }


    /**
     * 前台 - 新增或者更新患者
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusPatient.class),
        @ApiImplicitParam(name = "token", value = "header中的token"),
        @ApiImplicitParam(name = "id", value = "插入不传，更新传"),
        @ApiImplicitParam(name = "name", value = "患者名字"),
        @ApiImplicitParam(name = "idcard", value = "患者身份证号"),
        @ApiImplicitParam(name = "sex", value = "性别：1男，2女"),
        @ApiImplicitParam(name = "birthday", value = "出生日期"),
        @ApiImplicitParam(name = "addressCode", value = "所在地编码"),
        @ApiImplicitParam(name = "addressName", value = "所在地名称"),
        @ApiImplicitParam(name = "addressDetail", value = "详细地址"),
        @ApiImplicitParam(name = "defaultPatient", value = "默认就诊人；1是，0否"),
    })
    @PostMapping("insertOrUpdatePatient")
    @ApiOperation(value = "前台 - 新增或者更新患者")
    public Object insertOrUpdatePatient(BusPatient bus, HttpServletRequest request) throws Exception {
        return patientService.insertOrUpdatePatient(bus, request);
    }


    /**
     * 前台 - 根据id删除患者
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusEvaluate.class),
        @ApiImplicitParam(name = "token", value = "header中的token"),
        @ApiImplicitParam(name = "ids", value = "患者id，多个用,隔开"),
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
        @ApiImplicitParam(dataTypeClass = BusUserShippingAddress.class),
        @ApiImplicitParam(name = "token", value = "header中的token"),
    })
    @PostMapping("listUserShippingAddress")
    @ApiOperation(value = "前台 - 获取收货地址列表")
    public Object listUserShippingAddress(BusUserShippingAddress bus, Pageable pageable, HttpServletRequest request) throws Exception {
        return userShippingAddressService.listUserShippingAddress(bus, pageable, request);
    }


    /**
     * 前台 - 新增或者更新收货地址
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusUserShippingAddress.class),
        @ApiImplicitParam(name = "token", value = "header中的token"),
        @ApiImplicitParam(name = "id", value = "不传是新增，传了是更新"),
        @ApiImplicitParam(name = "name", value = "姓名"),
        @ApiImplicitParam(name = "mobile", value = "手机号码"),
        @ApiImplicitParam(name = "addressCode", value = "所在地编码"),
        @ApiImplicitParam(name = "addressName", value = "所在地名称"),
        @ApiImplicitParam(name = "addressDetail", value = "详细地址"),
        @ApiImplicitParam(name = "defaultAddress", value = "是否默认地址：1是，0否"),
    })
    @PostMapping("insertOrUpdateUserShippingAddress")
    @ApiOperation(value = "前台 - 新增或者更新收货地址")
    public Object insertOrUpdateUserShippingAddress(BusUserShippingAddress bus, HttpServletRequest request) throws Exception {
        return userShippingAddressService.insertOrUpdateUserShippingAddress(bus, request);
    }


    /**
     * 前台 - 根据id删除收货地址
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusUserShippingAddress.class),
        @ApiImplicitParam(name = "token", value = "header中的token"),
        @ApiImplicitParam(name = "ids", value = "地址id，多个用,隔开"),
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
        @ApiImplicitParam(dataTypeClass = BusUserShoppingCart.class),
        @ApiImplicitParam(name = "token", value = "header中的token"),
    })
    @PostMapping("listUserShippingCart")
    @ApiOperation(value = "前台 - 获取购物车的商品列表")
    public Object listUserShippingCart(BusUserShoppingCart bus, Pageable pageable, HttpServletRequest request) throws Exception {
        return userShoppingCartService.listUserShoppingCart(bus, pageable, request);
    }


    /**
     * 前台 - 新增或者更新购物车里的商品
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusUserShippingAddress.class),
        @ApiImplicitParam(name = "token", value = "header中的token"),
        @ApiImplicitParam(name = "id", value = "不传id新增，传id为更新"),

        @ApiImplicitParam(name = "goodsId", value = "商品id"),
        @ApiImplicitParam(name = "goodsName", value = "商品标题"),
        @ApiImplicitParam(name = "goodsNum", value = "购买商品数量"),
        @ApiImplicitParam(name = "goodsSpecification", value = "商品规格id"),

    })
    @PostMapping("insertOrUpdateUserShoppingCart")
    @ApiOperation(value = "前台 - 新增或者更新购物车里的商品")
    public Object insertOrUpdateUserShippingAddress(BusUserShoppingCart bus, HttpServletRequest request) throws Exception {
        return userShoppingCartService.insertOrUpdateUserShoppingCart(bus, request);
    }


    /**
     * 前台 - 根据id删除购物车里的商品
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusUserShippingAddress.class),
        @ApiImplicitParam(name = "token", value = "header中的token"),
        @ApiImplicitParam(name = "ids", value = "购物车id，多个用,隔开"),
    })
    @PostMapping("deleteUserShoppingCart")
    @ApiOperation(value = "前台 - 根据id删除购物车里的商品")
    public Object deleteUserShoppingCart(String ids) {
        return userShoppingCartService.deleteUserShoppingCart(ids);
    }


    /**
     * 前台 - 单商品下单
     *
     * @param goods
     * @param request
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusOrderGoods.class),
        @ApiImplicitParam(name = "token", value = "header中的token"),
        @ApiImplicitParam(name = "goodsId", value = "商品id"),
        @ApiImplicitParam(name = "goodsSpecificationId", value = "商品规格id"),
        @ApiImplicitParam(name = "goodsSpecificationName", value = "商品规格名字"),
        @ApiImplicitParam(name = "marketPrice", value = "市场单价"),
        @ApiImplicitParam(name = "price", value = "单价"),
        @ApiImplicitParam(name = "num", value = "购买数量"),
        @ApiImplicitParam(name = "totalAmount", value = "小订单金额=单价x购买数量"),
        @ApiImplicitParam(name = "remark", value = "下单备注"),
    })
    @PostMapping("insertOrder")
    @ApiOperation(value = "前台 - 单商品下单")
    public Object insertOrder(BusOrderGoods goods, HttpServletRequest request) throws Exception {
        return orderService.insertOrder(goods, request);
    }

    /**
     * 前台 - 购物车下单
     *
     * @param request
     * @param userShoppingCart
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusUserShoppingCart.class),
        @ApiImplicitParam(name = "token", value = "header中的token"),
        @ApiImplicitParam(name = "userShoppingCart", value = "json数组的购物车信息比如:[{'goodsId':'123456',},{}]"),

        @ApiImplicitParam(name = "goodsId", value = "商品id"),
        @ApiImplicitParam(name = "goodsName", value = "商品标题"),
        @ApiImplicitParam(name = "goodsNum", value = "购买商品数量"),
        @ApiImplicitParam(name = "goodsSpecification", value = "商品规格"),
    })
    @PostMapping("insertOrderWithShoppingCart")
    @ApiOperation(value = "前台 - 购物车下单")
    public Object insertOrderWithShoppingCart(String userShoppingCart, HttpServletRequest request) throws Exception {
        return orderService.insertOrderWithShoppingCart(userShoppingCart, request);
    }

    /**
     * 更新订单，支付，回调
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("updateOrderStateForPay")
    @ApiOperation(value = "更新订单，支付，回调")
    public Object updateOrderStateForPay(BusOrderGoodsTotal bus,HttpServletRequest request)throws Exception{
        return null;
    }


    /**
     * 获取用户的商品订单列表
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("listOrder")
    @ApiOperation(value = "获取用户的商品订单列表")
    public Object listOrder(BusOrderGoodsTotal bus,Pageable pageable,HttpServletRequest request)throws Exception{
        return orderService.listOrder(bus, pageable,request);
    }

}
