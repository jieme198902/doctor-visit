package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusPatient;
import com.doctor.visit.service.impl.PatientServiceImpl;
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
 * 患者接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@RestController
@Api("患者接口")
@RequestMapping(Constants.API_BASE_SYS + "/patient")
public class SysPatientResource {

    private final PatientServiceImpl patientService;

    public SysPatientResource(PatientServiceImpl patientService) {
        this.patientService = patientService;
    }


    /**
     * 查询患者列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusPatient.class)
    })
    @PostMapping("listPatient")
    @ApiOperation(value = "查询患者列表")
    public Object listPatient(BusPatient bus, Pageable pageable, HttpServletRequest request) throws Exception {
        return patientService.listPatient(bus, pageable,request,true);
    }


}
