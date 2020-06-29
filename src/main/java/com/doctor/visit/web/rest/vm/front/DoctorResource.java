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
public class DoctorResource {
    private final DoctorService doctorService;

    public DoctorResource(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * 获取医生列表
     *
     * @param busDoctor
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusDoctor.class)
    })
    @PostMapping("getDoctorList")
    @ApiOperation(value = "获取医生列表")
    public Object getDoctorList(BusDoctor busDoctor, Pageable pageable) {
        return doctorService.listDoctor(busDoctor, pageable);
    }

    /**
     * 获取关注的医生列表
     *
     * @param busDoctor
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusDoctor.class)
    })
    @PostMapping("getFavDoctorList")
    @ApiOperation(value = "获取关注的医生列表")
    public Object getFavDoctorList(BusDoctor busDoctor, Pageable pageable) {
        return doctorService.listFavDoctor(busDoctor, pageable);
    }

    /**
     * 关注医生
     *
     * @param busRelationUserDoctor
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusRelationUserDoctor.class)
    })
    @PostMapping("favDoctor")
    @ApiOperation(value = "关注医生")
    public Object favDoctor(BusRelationUserDoctor busRelationUserDoctor) {
        busRelationUserDoctor.setIsDel(Constants.EXIST);
        return doctorService.insertOrUpdateRelationUserDoctor(busRelationUserDoctor);
    }

    /**
     * 取消关注医生
     *
     * @param busRelationUserDoctor
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusArticle.class)
    })
    @PostMapping("cancelFavDoctor")
    @ApiOperation(value = "取消关注医生")
    public Object cancelFavDoctor(BusRelationUserDoctor busRelationUserDoctor) {
        busRelationUserDoctor.setIsDel(Constants.DELETE);
        return doctorService.insertOrUpdateRelationUserDoctor(busRelationUserDoctor);
    }

}
