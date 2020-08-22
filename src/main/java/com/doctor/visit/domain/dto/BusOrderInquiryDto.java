package com.doctor.visit.domain.dto;

import com.doctor.visit.domain.BusDoctor;
import com.doctor.visit.domain.BusOrderInquiry;
import com.doctor.visit.domain.BusPatient;

/**
 *
 */
public class BusOrderInquiryDto extends BusOrderInquiry {
    private String imgs;
    private BusPatient busPatient;
    private BusDoctorDto busDoctor;

    public BusDoctorDto getBusDoctor() {
        return busDoctor;
    }

    public void setBusDoctor(BusDoctorDto busDoctor) {
        this.busDoctor = busDoctor;
    }

    public BusPatient getBusPatient() {
        return busPatient;
    }

    public void setBusPatient(BusPatient busPatient) {
        this.busPatient = busPatient;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }
}
