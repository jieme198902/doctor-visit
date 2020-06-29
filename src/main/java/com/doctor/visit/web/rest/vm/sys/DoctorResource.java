package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusDoctor;
import com.doctor.visit.domain.BusHospital;
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
public class DoctorResource {

    private final DoctorService doctorService;

    public DoctorResource(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * 查询医生列表
     *
     * @param busDoctor
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusHospital.class)
    })
    @PostMapping("listDoctor")
    @ApiOperation(value = "查询医生列表")
    public Object listDoctor(BusDoctor busDoctor, Pageable pageable) {
        return doctorService.listDoctor(busDoctor, pageable);
    }

    /**
     * 新增或者修改医生
     *
     * @param busDoctor
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusHospital.class)
    })
    @PostMapping("insertOrUpdateDoctor")
    @ApiOperation(value = "新增或者修改医生")
    public Object insertOrUpdateDoctor(BusDoctor busDoctor, HttpServletRequest request) {
        return doctorService.insertOrUpdateDoctor(busDoctor, request);
    }

    /**
     * 根据id删除医生
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("deleteDoctor")
    @ApiOperation(value = "根据id删除医生")
    public Object deleteDoctor(String ids) {
        return doctorService.deleteDoctor(ids);
    }
}
