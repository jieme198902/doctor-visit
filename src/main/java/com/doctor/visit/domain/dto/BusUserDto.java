package com.doctor.visit.domain.dto;

import com.doctor.visit.domain.BusUser;

public class BusUserDto extends BusUser {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
