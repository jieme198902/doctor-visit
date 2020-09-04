package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusHospital;
import com.doctor.visit.service.impl.HospitalServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信医院的接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@Api("微信医院的接口")
@RestController
@RequestMapping(Constants.API_BASE_FRONT + "/hospital")
public class FrontHospitalResource {

    private final HospitalServiceImpl hospitalService;


    public FrontHospitalResource(HospitalServiceImpl hospitalService) {
        this.hospitalService = hospitalService;
    }

    /**
     * 查询医院列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusHospital.class),
        @ApiImplicitParam(name = "name",value = "医院名称"),
        @ApiImplicitParam(name = "lat",value = "纬度，如果传了则返回isDel为距离，不传不返回"),
        @ApiImplicitParam(name = "lng",value = "经度，如果传了则返回isDel为距离，不传不返回")
    })
    @PostMapping("listHospital")
    @ApiOperation(value = "查询医院列表")
    public Object listHospital(BusHospital bus, Pageable pageable) {
        return hospitalService.listHospital(bus, pageable);
    }
}
