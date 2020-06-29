package com.doctor.visit.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "bus_evaluate")
public class BusEvaluate implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 评分0-5
     */
    private Integer score;

    /**
     * 评价内容
     */
    private String evaluate;

    /**
     * 图片
     */
    private String img;

    /**
     * 模块：0医生，1商品
     */
    private String module;

    /**
     * 医生id，商品id
     */
    @Column(name = "module_id")
    private Long moduleId;

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
     * 获取评分0-5
     *
     * @return score - 评分0-5
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置评分0-5
     *
     * @param score 评分0-5
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取评价内容
     *
     * @return evaluate - 评价内容
     */
    public String getEvaluate() {
        return evaluate;
    }

    /**
     * 设置评价内容
     *
     * @param evaluate 评价内容
     */
    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    /**
     * 获取图片
     *
     * @return img - 图片
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置图片
     *
     * @param img 图片
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 获取模块：0医生，1商品
     *
     * @return module - 模块：0医生，1商品
     */
    public String getModule() {
        return module;
    }

    /**
     * 设置模块：0医生，1商品
     *
     * @param module 模块：0医生，1商品
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * 获取医生id，商品id
     *
     * @return module_id - 医生id，商品id
     */
    public Long getModuleId() {
        return moduleId;
    }

    /**
     * 设置医生id，商品id
     *
     * @param moduleId 医生id，商品id
     */
    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
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
