package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusEvaluate;
import com.doctor.visit.domain.BusPatient;
import com.doctor.visit.service.MineService;
import com.doctor.visit.service.PatientService;
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

    public FrontMineResource(MineService mineService, PatientService patientService) {
        this.mineService = mineService;
        this.patientService = patientService;
    }


    /**
     * 评价接口，评价医生、商品
     * <p>
     * module  模块：0医生，1商品
     *
     * @param busEvaluate
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusEvaluate.class)
    })
    @PostMapping("insertOrUpdateEvaluate")
    @ApiOperation(value = "评价接口，评价医生、商品 --> module  模块：0医生，1商品")
    public Object insertOrUpdateEvaluate(BusEvaluate busEvaluate, HttpServletRequest request) {
        return mineService.insertOrUpdateEvaluate(busEvaluate, request);
    }


    /**
     * 前台 - 获取患者列表
     *
     * @param busPatient
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusPatient.class)
    })
    @PostMapping("listPatient")
    @ApiOperation(value = "前台 - 获取患者列表")
    public Object listPatient(BusPatient busPatient, Pageable pageable) {
        return patientService.listPatient(busPatient, pageable);
    }


    /**
     * 前台 - 新增或者更新患者
     *
     * @param busPatient
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusPatient.class)
    })
    @PostMapping("insertOrUpdatePatient")
    @ApiOperation(value = "前台 - 新增或者更新患者")
    public Object insertOrUpdatePatient(BusPatient busPatient, HttpServletRequest request) {
        return patientService.insertOrUpdatePatient(busPatient, request);
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



}
