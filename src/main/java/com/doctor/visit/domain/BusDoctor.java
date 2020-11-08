package com.doctor.visit.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "bus_doctor")
public class BusDoctor implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 医师姓名
     */
    private String name;



    /**
     * 医生职称 对应dict 的YSZC
     */
    @Column(name = "professional_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long professionalId;
    /**
     * 医生职称
     */
    @Column(name = "professional_title")
    private String professionalTitle;

    /**
     * 医生所属医院名称
     */
    @Column(name = "hospital_name")
    private String hospitalName;

    /**
     * 医生所属医院id
     */
    @Column(name = "hospital_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long hospitalId;

    /**
     * '医生类别：0中医、1西医、2中西医',
     */
    @Column(name = "doctor_type")
    private String doctorType;
    /**
     * 是否名医：1是，0否
     */
    @Column(name = "famous_doctor")
    private String famousDoctor;

    /**
     * 擅长描述
     */
    @Column(name = "good_at")
    private String goodAt;

    /**
     * 咨询量
     */
    private Integer inquiries;

    /**
     * 咨询费(单位分)
     */
    @Column(name = "consulting_fees")
    private Integer consultingFees;

    /**
     * 门诊科室名称
     */
    @Column(name = "clinc_name")
    private String clincName;

    /**
     * 门诊科室id
     */
    @Column(name = "clinc_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long clincId;

    /**
     * 创建者id
     */
    @Column(name = "create_by")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createBy;

    /**
     * 创建者姓名
     */
    @Column(name = "create_name")
    private String createName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")  //取日期时使用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//存日期时使用
    private Date createTime;

    /**
     * 修改人
     */
    @Column(name = "edit_by")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long editBy;

    /**
     * 修改人姓名
     */
    @Column(name = "edit_name")
    private String editName;

    /**
     * 修改时间
     */
    @Column(name = "edit_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")  //取日期时使用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//存日期时使用
    private Date editTime;

    /**
     * 是否删除，1是0否
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
     * 获取医师姓名
     *
     * @return name - 医师姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置医师姓名
     *
     * @param name 医师姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 医生职称 对应dict 的YSZC
     * @return
     */
    public Long getProfessionalId() {
        return professionalId;
    }

    /**
     * 医生职称 对应dict 的YSZC
     * @param professionalId
     */
    public void setProfessionalId(Long professionalId) {
        this.professionalId = professionalId;
    }

    /**
     * 获取医生职称
     *
     * @return professional_title - 医生职称
     */
    public String getProfessionalTitle() {
        return professionalTitle;
    }

    /**
     * 设置医生职称
     *
     * @param professionalTitle 医生职称
     */
    public void setProfessionalTitle(String professionalTitle) {
        this.professionalTitle = professionalTitle;
    }

    /**
     * 获取医生所属医院名称
     *
     * @return hospital_name - 医生所属医院名称
     */
    public String getHospitalName() {
        return hospitalName;
    }

    /**
     * 设置医生所属医院名称
     *
     * @param hospitalName 医生所属医院名称
     */
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    /**
     * 获取医生所属医院id
     *
     * @return hospital_id - 医生所属医院id
     */
    public Long getHospitalId() {
        return hospitalId;
    }

    /**
     * 设置医生所属医院id
     *
     * @param hospitalId 医生所属医院id
     */
    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    /**
     * 医生类别：0中医、1西医、2中西医
     * @return
     */
    public String getDoctorType() {
        return doctorType;
    }

    /**
     * 医生类别：0中医、1西医、2中西医
     * @param doctorType
     */
    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    /**
     * 是否名医：1是，0否
     * @return
     */
    public String getFamousDoctor() {
        return famousDoctor;
    }

    /**
     * 是否名医：1是，0否
     * @param famousDoctor
     */
    public void setFamousDoctor(String famousDoctor) {
        this.famousDoctor = famousDoctor;
    }

    /**
     * 获取擅长描述
     *
     * @return good_at - 擅长描述
     */
    public String getGoodAt() {
        return goodAt;
    }

    /**
     * 设置擅长描述
     *
     * @param goodAt 擅长描述
     */
    public void setGoodAt(String goodAt) {
        this.goodAt = goodAt;
    }

    /**
     * 获取咨询量
     *
     * @return inquiries - 咨询量
     */
    public Integer getInquiries() {
        return inquiries;
    }

    /**
     * 设置咨询量
     *
     * @param inquiries 咨询量
     */
    public void setInquiries(Integer inquiries) {
        this.inquiries = inquiries;
    }

    /**
     * 获取咨询费(单位分)
     *
     * @return consulting_fees - 咨询费(单位分)
     */
    public Integer getConsultingFees() {
        return consultingFees;
    }

    /**
     * 设置咨询费(单位分)
     *
     * @param consultingFees 咨询费(单位分)
     */
    public void setConsultingFees(Integer consultingFees) {
        this.consultingFees = consultingFees;
    }

    /**
     * 获取门诊科室名称
     *
     * @return clinc_name - 门诊科室名称
     */
    public String getClincName() {
        return clincName;
    }

    /**
     * 设置门诊科室名称
     *
     * @param clincName 门诊科室名称
     */
    public void setClincName(String clincName) {
        this.clincName = clincName;
    }

    /**
     * 获取门诊科室id
     *
     * @return clinc_id - 门诊科室id
     */
    public Long getClincId() {
        return clincId;
    }

    /**
     * 设置门诊科室id
     *
     * @param clincId 门诊科室id
     */
    public void setClincId(Long clincId) {
        this.clincId = clincId;
    }

    /**
     * 获取创建者id
     *
     * @return create_by - 创建者id
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建者id
     *
     * @param createBy 创建者id
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建者姓名
     *
     * @return create_name - 创建者姓名
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * 设置创建者姓名
     *
     * @param createName 创建者姓名
     */
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改人
     *
     * @return edit_by - 修改人
     */
    public Long getEditBy() {
        return editBy;
    }

    /**
     * 设置修改人
     *
     * @param editBy 修改人
     */
    public void setEditBy(Long editBy) {
        this.editBy = editBy;
    }

    /**
     * 获取修改人姓名
     *
     * @return edit_name - 修改人姓名
     */
    public String getEditName() {
        return editName;
    }

    /**
     * 设置修改人姓名
     *
     * @param editName 修改人姓名
     */
    public void setEditName(String editName) {
        this.editName = editName;
    }

    /**
     * 获取修改时间
     *
     * @return edit_time - 修改时间
     */
    public Date getEditTime() {
        return editTime;
    }

    /**
     * 设置修改时间
     *
     * @param editTime 修改时间
     */
    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    /**
     * 获取是否删除，1是0否
     *
     * @return is_del - 是否删除，1是0否
     */
    public String getIsDel() {
        return isDel;
    }

    /**
     * 设置是否删除，1是0否
     *
     * @param isDel 是否删除，1是0否
     */
    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }
}
