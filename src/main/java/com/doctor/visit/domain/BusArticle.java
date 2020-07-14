package com.doctor.visit.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "bus_article")
public class BusArticle implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 文章名称
     */
    private String title;

    /**
     * 文章类型id
     */
    @Column(name = "class_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long classId;

    /**
     * 文章类型名称
     */
    @Column(name = "class_name")
    private String className;

    /**
     * 文章内容url
     */
    private String url;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章转发哪里
     */
    @Column(name = "forward_from")
    private String forwardFrom;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")  //取日期时使用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//存日期时使用
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")  //取日期时使用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//存日期时使用
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
     * 获取文章名称
     *
     * @return title- 文章名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置文章名称
     *
     * @param title 文章名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 文章转发哪里
     */
    public String getForwardFrom() {
        return forwardFrom;
    }

    /**
     * 文章转发哪里
     */
    public void setForwardFrom(String forwardFrom) {
        this.forwardFrom = forwardFrom;
    }

    /**
     * 获取文章类型id
     *
     * @return class_id - 文章类型id
     */
    public Long getClassId() {
        return classId;
    }

    /**
     * 设置文章类型id
     *
     * @param classId 文章类型id
     */
    public void setClassId(Long classId) {
        this.classId = classId;
    }

    /**
     * 获取文章类型名称
     *
     * @return class_name - 文章类型名称
     */
    public String getClassName() {
        return className;
    }

    /**
     * 设置文章类型名称
     *
     * @param className 文章类型名称
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 获取文章内容url
     *
     * @return url - 文章内容url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置文章内容url
     *
     * @param url 文章内容url
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * 设置文章内容
     *
     * @return content 文章内容
     */
    public String getContent() {
        return content;
    }
    /**
     * 设置文章内容
     *
     * @param content 文章内容
     */
    public void setContent(String content) {
        this.content = content;
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
