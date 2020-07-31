package com.doctor.visit.domain.dto;

import com.doctor.visit.domain.BusDoctor;

/**
 *
 */
public class BusDoctorDto extends BusDoctor {
    private String img;
    private String attention;

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
