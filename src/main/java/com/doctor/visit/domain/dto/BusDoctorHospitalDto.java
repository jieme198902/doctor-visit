package com.doctor.visit.domain.dto;


import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索的医院和医生的接口
 */
public class BusDoctorHospitalDto implements Serializable {
    private List<BusDoctorDto> busDoctorDtos;
    private List<BusHospitalDto> busHospitalDtos;

    public List<BusDoctorDto> getBusDoctorDtos() {
        return busDoctorDtos;
    }

    public void setBusDoctorDtos(List<BusDoctorDto> busDoctorDtos) {
        this.busDoctorDtos = busDoctorDtos;
    }

    public List<BusHospitalDto> getBusHospitalDtos() {
        return busHospitalDtos;
    }

    public void setBusHospitalDtos(List<BusHospitalDto> busHospitalDtos) {
        this.busHospitalDtos = busHospitalDtos;
    }
}
