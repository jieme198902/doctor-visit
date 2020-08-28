package com.doctor.visit.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_button")
public class SysButton implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 按钮名称
     */
    private String name;

    /**
     * 按钮编码
     */
    private String code;

    /**
     * 按钮类型：0顶部按钮，1行级按钮
     */
    @Column(name = "btn_type")
    private String btnType;

    /**
     * 按钮样式
     */
    @Column(name = "btn_class")
    private String btnClass;

    /**
     * 排序
     */
    @Column(name = "sort_by")
    private Integer sortBy;

    /**
     * 创建者id
     */
    @Column(name = "create_by")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")  //取日期时使用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//存日期时使用
    private Date createTime;

    /**
     * 修改人
     */
    @Column(name = "edit_by")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")  //取日期时使用
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
     * 获取按钮名称
     *
     * @return name - 按钮名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置按钮名称
     *
     * @param name 按钮名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取按钮编码
     *
     * @return code - 按钮编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置按钮编码
     *
     * @param code 按钮编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取按钮类型：0顶部按钮，1行级按钮
     *
     * @return btn_type - 按钮类型：0顶部按钮，1行级按钮
     */
    public String getBtnType() {
        return btnType;
    }

    /**
     * 设置按钮类型：0顶部按钮，1行级按钮
     *
     * @param btnType 按钮类型：0顶部按钮，1行级按钮
     */
    public void setBtnType(String btnType) {
        this.btnType = btnType;
    }

    /**
     * 获取按钮样式
     *
     * @return btn_class - 按钮样式
     */
    public String getBtnClass() {
        return btnClass;
    }

    /**
     * 设置按钮样式
     *
     * @param btnClass 按钮样式
     */
    public void setBtnClass(String btnClass) {
        this.btnClass = btnClass;
    }

    /**
     * 获取排序
     *
     * @return sort_by - 排序
     */
    public Integer getSortBy() {
        return sortBy;
    }

    /**
     * 设置排序
     *
     * @param sortBy 排序
     */
    public void setSortBy(Integer sortBy) {
        this.sortBy = sortBy;
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
