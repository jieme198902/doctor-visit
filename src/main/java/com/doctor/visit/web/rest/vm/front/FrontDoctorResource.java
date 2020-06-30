package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusArticle;
import com.doctor.visit.domain.BusDoctor;
import com.doctor.visit.domain.BusRelationUserArticle;
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
     * 获取医生列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusDoctor.class)
    })
    @PostMapping("getDoctorList")
    @ApiOperation(value = "获取医生列表")
    public Object getDoctorList(BusDoctor bus, Pageable pageable) {
        return doctorService.listDoctor(bus, pageable);
    }

    /**
     * 获取关注的医生列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusDoctor.class)
    })
    @PostMapping("getFavDoctorList")
    @ApiOperation(value = "获取关注的医生列表")
    public Object getFavDoctorList(BusDoctor bus, Pageable pageable) {
        return doctorService.listFavDoctor(bus, pageable);
    }

    /**
     * 关注医生
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusRelationUserDoctor.class)
    })
    @PostMapping("favDoctor")
    @ApiOperation(value = "关注医生")
    public Object favDoctor(BusRelationUserDoctor bus) {
        bus.setIsDel(Constants.EXIST);
        return doctorService.insertOrUpdateRelationUserDoctor(bus);
    }

    /**
     * 取消关注医生
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusArticle.class)
    })
    @PostMapping("cancelFavDoctor")
    @ApiOperation(value = "取消关注医生")
    public Object cancelFavDoctor(BusRelationUserDoctor bus) {
        bus.setIsDel(Constants.DELETE);
        return doctorService.insertOrUpdateRelationUserDoctor(bus);
    }

}
