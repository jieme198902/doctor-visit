package com.doctor.visit.domain.dto;

import com.doctor.visit.domain.BusHospital;

public class BusHospitalDto extends BusHospital {
    private String distance;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
