package com.doctor.visit.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "wk_fxt_insulin_fee")
public class WkFxtInsulinFee implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 床号
     */
    @Column(name = "bed_no")
    private Integer bedNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 住院号
     */
    @Column(name = "hospital_no")
    private Integer hospitalNo;

    /**
     * 医疗分组
     */
    @Column(name = "group_no")
    private Integer groupNo;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")  //取日期时使用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//存日期时使用
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")  //取日期时使用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//存日期时使用
    private Date endTime;

    /**
     * 总时间
     */
    @Column(name = "total_time")
    private BigDecimal totalTime;

    /**
     * 总费用
     */
    @Column(name = "total_fee")
    private BigDecimal totalFee;

    /**
     * 医疗费
     */
    @Column(name = "medical_fee")
    private BigDecimal medicalFee;

    /**
     * 护理费
     */
    @Column(name = "nurse_fee")
    private BigDecimal nurseFee;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")  //取日期时使用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//存日期时使用
    private Date createTime;

    @Column(name = "yearmonth")
    private String yearmonth;

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
     * 获取床号
     *
     * @return bed_no - 床号
     */
    public Integer getBedNo() {
        return bedNo;
    }

    /**
     * 设置床号
     *
     * @param bedNo 床号
     */
    public void setBedNo(Integer bedNo) {
        this.bedNo = bedNo;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取住院号
     *
     * @return hospital_no - 住院号
     */
    public Integer getHospitalNo() {
        return hospitalNo;
    }

    /**
     * 设置住院号
     *
     * @param hospitalNo 住院号
     */
    public void setHospitalNo(Integer hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    /**
     * 获取医疗分组
     *
     * @return group_no - 医疗分组
     */
    public Integer getGroupNo() {
        return groupNo;
    }

    /**
     * 设置医疗分组
     *
     * @param groupNo 医疗分组
     */
    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
    }

    /**
     * 获取开始时间
     *
     * @return start_time - 开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间
     *
     * @param startTime 开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束时间
     *
     * @return end_time - 结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置结束时间
     *
     * @param endTime 结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取总时间
     *
     * @return total_time - 总时间
     */
    public BigDecimal getTotalTime() {
        return totalTime;
    }

    /**
     * 设置总时间
     *
     * @param totalTime 总时间
     */
    public void setTotalTime(BigDecimal totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * 获取总费用
     *
     * @return total_fee - 总费用
     */
    public BigDecimal getTotalFee() {
        return totalFee;
    }

    /**
     * 设置总费用
     *
     * @param totalFee 总费用
     */
    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * 获取医疗费
     *
     * @return medical_fee - 医疗费
     */
    public BigDecimal getMedicalFee() {
        return medicalFee;
    }

    /**
     * 设置医疗费
     *
     * @param medicalFee 医疗费
     */
    public void setMedicalFee(BigDecimal medicalFee) {
        this.medicalFee = medicalFee;
    }

    /**
     * 获取护理费
     *
     * @return nurse_fee - 护理费
     */
    public BigDecimal getNurseFee() {
        return nurseFee;
    }

    /**
     * 设置护理费
     *
     * @param nurseFee 护理费
     */
    public void setNurseFee(BigDecimal nurseFee) {
        this.nurseFee = nurseFee;
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

    public String getYearmonth() {
        return yearmonth;
    }

    public void setYearmonth(String yearmonth) {
        this.yearmonth = yearmonth;
    }
}
