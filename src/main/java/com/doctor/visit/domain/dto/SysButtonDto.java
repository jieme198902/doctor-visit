package com.doctor.visit.domain.dto;

import com.doctor.visit.domain.SysButton;

public class SysButtonDto extends SysButton {
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
