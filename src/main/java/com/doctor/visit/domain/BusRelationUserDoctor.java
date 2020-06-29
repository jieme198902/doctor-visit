package com.doctor.visit.domain;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "bus_relation_user_doctor")
public class BusRelationUserDoctor implements Serializable {
    @Id
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 医生id
     */
    @Column(name = "doctor_id")
    private Long doctorId;

    /**
     * 是否删除，1是，0否
     */
    @Column(name = "is_del")
    private String isDel;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取医生id
     *
     * @return doctor_id - 医生id
     */
    public Long getDoctorId() {
        return doctorId;
    }

    /**
     * 设置医生id
     *
     * @param doctorId 医生id
     */
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * 获取是否删除，1是，0否
     *
     * @return is_del - 是否删除，1是，0否
     */
    public String getIsDel() {
        return isDel;
    }

    /**
     * 设置是否删除，1是，0否
     *
     * @param isDel 是否删除，1是，0否
     */
    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }
}