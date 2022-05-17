package com.doctor.visit.domain.param;

import java.io.Serializable;

/**
 * 用户id，医生id
 */
public class UserDoctorParam implements Serializable {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 医生id
     */
    private Long doctorId;
    /**
     * 用户id
     */
    public Long getUserId() {
        return userId;
    }
    /**
     * 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    /**
     * 医生id
     */
    public Long getDoctorId() {
        return doctorId;
    }
    /**
     * 医生id
     */
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
