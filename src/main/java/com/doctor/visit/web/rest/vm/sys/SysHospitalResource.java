package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusHospital;
import com.doctor.visit.domain.BusHospital;
import com.doctor.visit.service.HospitalService;
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
 * 医院的接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@Api("微信医院的接口")
@RestController
@RequestMapping(Constants.API_BASE_SYS + "/hospital")
public class SysHospitalResource {

    private final HospitalService hospitalService;


    public SysHospitalResource(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }


    /**
     * 查询医院列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusHospital.class)
    })
    @PostMapping("listBusHospital")
    @ApiOperation(value = "查询医院列表")
    public Object listBusHospital(BusHospital bus, Pageable pageable) {
        return hospitalService.listHospital(bus, pageable);
    }

    /**
     * 新增或者修改医院
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusHospital.class)
    })
    @PostMapping("insertOrUpdateBusHospital")
    @ApiOperation(value = "新增或者修改医院")
    public Object insertOrUpdateBusHospital(BusHospital bus, HttpServletRequest request) {
        return hospitalService.insertOrUpdateHospital(bus,request);
    }

    /**
     * 根据id删除医院
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("deleteBusHospital")
    @ApiOperation(value = "根据id删除医院")
    public Object deleteBusHospital(String ids) {
        return hospitalService.deleteHospital(ids);
    }
}
