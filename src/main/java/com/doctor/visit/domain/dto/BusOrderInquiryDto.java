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
    private BusDoctor busDoctor;

    public BusDoctor getBusDoctor() {
        return busDoctor;
    }

    public void setBusDoctor(BusDoctor busDoctor) {
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
