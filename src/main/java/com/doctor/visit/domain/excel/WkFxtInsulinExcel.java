package com.doctor.visit.domain.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WkFxtInsulinExcel implements Serializable{
    @ExcelProperty({"山东大学第二医院内分泌胰岛素泵费用明细", "床号"})
    private String bedNo;
    @ExcelProperty({"山东大学第二医院内分泌胰岛素泵费用明细", "姓名"})
    private String name;
    @ExcelProperty({"山东大学第二医院内分泌胰岛素泵费用明细", "住院号"})
    private String hospitalNo;
    @ExcelProperty({"山东大学第二医院内分泌胰岛素泵费用明细", "医疗分组"})
    private String groupNo;
    @ExcelProperty({"山东大学第二医院内分泌胰岛素泵费用明细", "开始时间"})
    private Date startTime;
    @ExcelProperty({"山东大学第二医院内分泌胰岛素泵费用明细", "结束时间"})
    private Date endTime;
    @ExcelProperty({"山东大学第二医院内分泌胰岛素泵费用明细", "总时间"})
    private BigDecimal totalTime;
    @ExcelProperty({"山东大学第二医院内分泌胰岛素泵费用明细", "总费用"})
    private BigDecimal totalFee;
    @ExcelProperty({"山东大学第二医院内分泌胰岛素泵费用明细", "医疗费用"})
    private BigDecimal medicalFee;
    @ExcelProperty({"山东大学第二医院内分泌胰岛素泵费用明细", "护理费用"})
    private BigDecimal nurseFee;

    @ExcelIgnore
    private BigDecimal monthTotalTime = new BigDecimal("0.0");
    @ExcelIgnore
    private BigDecimal monthTotalFee= new BigDecimal("0.0");;
    @ExcelIgnore
    private BigDecimal monthTotalMedicalFee= new BigDecimal("0.0");;
    @ExcelIgnore
    private BigDecimal monthTotalNurseFee= new BigDecimal("0.0");;

    public BigDecimal getMonthTotalTime() {
        return monthTotalTime;
    }

    public void setMonthTotalTime(BigDecimal monthTotalTime) {
        this.monthTotalTime = monthTotalTime;
    }

    public BigDecimal getMonthTotalFee() {
        return monthTotalFee;
    }

    public void setMonthTotalFee(BigDecimal monthTotalFee) {
        this.monthTotalFee = monthTotalFee;
    }

    public BigDecimal getMonthTotalMedicalFee() {
        return monthTotalMedicalFee;
    }

    public void setMonthTotalMedicalFee(BigDecimal monthTotalMedicalFee) {
        this.monthTotalMedicalFee = monthTotalMedicalFee;
    }

    public BigDecimal getMonthTotalNurseFee() {
        return monthTotalNurseFee;
    }

    public void setMonthTotalNurseFee(BigDecimal monthTotalNurseFee) {
        this.monthTotalNurseFee = monthTotalNurseFee;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(BigDecimal totalTime) {
        this.totalTime = totalTime;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getMedicalFee() {
        return medicalFee;
    }

    public void setMedicalFee(BigDecimal medicalFee) {
        this.medicalFee = medicalFee;
    }

    public BigDecimal getNurseFee() {
        return nurseFee;
    }

    public void setNurseFee(BigDecimal nurseFee) {
        this.nurseFee = nurseFee;
    }
}
