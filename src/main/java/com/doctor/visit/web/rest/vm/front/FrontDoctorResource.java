package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusDoctor;
import com.doctor.visit.domain.BusHospital;
import com.doctor.visit.domain.BusRelationUserDoctor;
import com.doctor.visit.service.DoctorService;
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
 * 微信医生的接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@Api("微信医生的接口")
@RestController
@RequestMapping(Constants.API_BASE_FRONT + "/doctor")
public class FrontDoctorResource {
    private final DoctorService doctorService;

    public FrontDoctorResource(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * 获取医生和医院列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusHospital.class),
        @ApiImplicitParam(name = "name",value = "搜索的内容"),
    })
    @PostMapping("listDoctorOrHospital")
    @ApiOperation(value = "获取医生和医院列表")
    public Object listDoctorOrHospital(BusHospital bus, Pageable pageable, HttpServletRequest request) throws Exception {
        return doctorService.listDoctorOrHospital(bus, pageable,request);
    }

    /**
     * 获取医生列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusDoctor.class),
        @ApiImplicitParam(name = "name",value = "医生名称"),
        @ApiImplicitParam(name = "clincId",value = "科室id"),
    })
    @PostMapping("getDoctorList")
    @ApiOperation(value = "获取医生列表")
    public Object getDoctorList(BusDoctor bus, Pageable pageable,HttpServletRequest request) throws Exception {
        return doctorService.listDoctor(bus, pageable,request,false);
    }

    /**
     * 获取关注的医生列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusDoctor.class),
        @ApiImplicitParam(name = "token",value = "header中的token"),
    })
    @PostMapping("getFavDoctorList")
    @ApiOperation(value = "获取关注的医生列表")
    public Object getFavDoctorList(BusDoctor bus, Pageable pageable, HttpServletRequest request) throws Exception {
        return doctorService.listFavDoctor(bus, pageable,request);
    }

    /**
     * 关注医生
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusRelationUserDoctor.class),
        @ApiImplicitParam(name = "token",value = "header中的token"),
        @ApiImplicitParam(name = "doctorId",value = "医生id"),
    })
    @PostMapping("favDoctor")
    @ApiOperation(value = "关注医生")
    public Object favDoctor(BusRelationUserDoctor bus,HttpServletRequest request) throws Exception {
        bus.setIsDel(Constants.EXIST);
        return doctorService.insertOrUpdateRelationUserDoctor(bus,request);
    }

    /**
     * 取消关注医生
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusRelationUserDoctor.class),
        @ApiImplicitParam(name = "token",value = "header中的token"),
        @ApiImplicitParam(name = "doctorId",value = "医生id"),
    })
    @PostMapping("cancelFavDoctor")
    @ApiOperation(value = "取消关注医生")
    public Object cancelFavDoctor(BusRelationUserDoctor bus,HttpServletRequest request) throws Exception {
        bus.setIsDel(Constants.DELETE);
        return doctorService.insertOrUpdateRelationUserDoctor(bus,request);
    }

}
