package com.doctor.visit.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "bus_hospital")
public class BusHospital implements Serializable {
    @Id
    private Integer id;

    /**
     * 医院名称
     */
    private String name;

    /**
     * 医院图片
     */
    private String img;

    /**
     * 医院等级
     */
    private String grade;

    /**
     * 医院类型：综合医院等
     */
    private String type;

    /**
     * 维度
     */
    private String lat;

    /**
     * 经度
     */
    private String lng;

    /**
     * 医院地址
     */
    private String address;

    /**
     * 医院手机号
     */
    private String phone;

    /**
     * 所属城市
     */
    private String area;

    /**
     * 医院咨询量
     */
    private String inquiries;

    /**
     * 创建者id
     */
    @Column(name = "create_by")
    private Integer createBy;

    /**
     * 创建者姓名
     */
    @Column(name = "create_name")
    private String createName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改人
     */
    @Column(name = "edit_by")
    private Integer editBy;

    /**
     * 修改人姓名
     */
    @Column(name = "edit_name")
    private String editName;

    /**
     * 修改时间
     */
    @Column(name = "edit_time")
    private Date editTime;

    /**
     * 是否删除，1是0否
     */
    @Column(name = "is_del")
    private String isDel;

    /**
     * 医院介绍
     */
    private String introduce;

    /**
     * 特色科室
     */
    private String features;

    /**
     * 特色诊疗手段
     */
    @Column(name = "diagnostic_methods")
    private String diagnosticMethods;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取医院名称
     *
     * @return name - 医院名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置医院名称
     *
     * @param name 医院名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取医院图片
     *
     * @return img - 医院图片
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置医院图片
     *
     * @param img 医院图片
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 获取医院等级
     *
     * @return grade - 医院等级
     */
    public String getGrade() {
        return grade;
    }

    /**
     * 设置医院等级
     *
     * @param grade 医院等级
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * 获取医院类型：综合医院等
     *
     * @return type - 医院类型：综合医院等
     */
    public String getType() {
        return type;
    }

    /**
     * 设置医院类型：综合医院等
     *
     * @param type 医院类型：综合医院等
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取维度
     *
     * @return lat - 维度
     */
    public String getLat() {
        return lat;
    }

    /**
     * 设置维度
     *
     * @param lat 维度
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * 获取经度
     *
     * @return lng - 经度
     */
    public String getLng() {
        return lng;
    }

    /**
     * 设置经度
     *
     * @param lng 经度
     */
    public void setLng(String lng) {
        this.lng = lng;
    }

    /**
     * 获取医院地址
     *
     * @return address - 医院地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置医院地址
     *
     * @param address 医院地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取医院手机号
     *
     * @return phone - 医院手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置医院手机号
     *
     * @param phone 医院手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取所属城市
     *
     * @return area - 所属城市
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置所属城市
     *
     * @param area 所属城市
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 获取医院咨询量
     *
     * @return inquiries - 医院咨询量
     */
    public String getInquiries() {
        return inquiries;
    }

    /**
     * 设置医院咨询量
     *
     * @param inquiries 医院咨询量
     */
    public void setInquiries(String inquiries) {
        this.inquiries = inquiries;
    }

    /**
     * 获取创建者id
     *
     * @return create_by - 创建者id
     */
    public Integer getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建者id
     *
     * @param createBy 创建者id
     */
    public void setCreateBy(Integer createBy) {
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
    public Integer getEditBy() {
        return editBy;
    }

    /**
     * 设置修改人
     *
     * @param editBy 修改人
     */
    public void setEditBy(Integer editBy) {
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

    /**
     * 获取医院介绍
     *
     * @return introduce - 医院介绍
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * 设置医院介绍
     *
     * @param introduce 医院介绍
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * 获取特色科室
     *
     * @return features - 特色科室
     */
    public String getFeatures() {
        return features;
    }

    /**
     * 设置特色科室
     *
     * @param features 特色科室
     */
    public void setFeatures(String features) {
        this.features = features;
    }

    /**
     * 获取特色诊疗手段
     *
     * @return diagnostic_methods - 特色诊疗手段
     */
    public String getDiagnosticMethods() {
        return diagnosticMethods;
    }

    /**
     * 设置特色诊疗手段
     *
     * @param diagnosticMethods 特色诊疗手段
     */
    public void setDiagnosticMethods(String diagnosticMethods) {
        this.diagnosticMethods = diagnosticMethods;
    }
}