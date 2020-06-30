package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusEvaluate;
import com.doctor.visit.domain.BusPatient;
import com.doctor.visit.domain.BusUserShippingAddress;
import com.doctor.visit.service.MineService;
import com.doctor.visit.service.PatientService;
import com.doctor.visit.service.UserShippingAddressService;
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
 * 前端 我的接口
 */
@RestController
@Api("微信我的的接口")
@RequestMapping(Constants.API_BASE_FRONT + "/mine")
public class FrontMineResource {

    private final MineService mineService;
    private final PatientService patientService;
    private final UserShippingAddressService userShippingAddressService;
    public FrontMineResource(MineService mineService, PatientService patientService, UserShippingAddressService userShippingAddressService) {
        this.mineService = mineService;
        this.patientService = patientService;
        this.userShippingAddressService = userShippingAddressService;
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
    public Object insertOrUpdateEvaluate(BusEvaluate bus, HttpServletRequest request) {
        return mineService.insertOrUpdateEvaluate(bus, request);
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
    public Object listPatient(BusPatient bus, Pageable pageable) {
        return patientService.listPatient(bus, pageable);
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



}
