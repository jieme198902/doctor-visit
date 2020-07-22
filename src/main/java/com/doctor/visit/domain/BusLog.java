package com.doctor.visit.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "bus_log")
public class BusLog implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 是否后台：1是，0否
     */
    private String sys;

    /**
     * 模块名称
     */
    private String model;

    /**
     * 操作，增删改查
     */
    private String operation;

    /**
     * 操作人id
     */
    @Column(name = "create_by")
    private Long createBy;

    /**
     * 操作人姓名
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
     * 操作信息
     */
    private String request;

    /**
     * 返回信息
     */
    private String response;

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
     * 获取是否后台：1是，0否
     *
     * @return sys - 是否后台：1是，0否
     */
    public String getSys() {
        return sys;
    }

    /**
     * 设置是否后台：1是，0否
     *
     * @param sys 是否后台：1是，0否
     */
    public void setSys(String sys) {
        this.sys = sys;
    }

    /**
     * 获取模块名称
     *
     * @return model - 模块名称
     */
    public String getModel() {
        return model;
    }

    /**
     * 设置模块名称
     *
     * @param model 模块名称
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 获取操作，增删改查
     *
     * @return operation - 操作，增删改查
     */
    public String getOperation() {
        return operation;
    }

    /**
     * 设置操作，增删改查
     *
     * @param operation 操作，增删改查
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * 获取操作人id
     *
     * @return create_by - 操作人id
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * 设置操作人id
     *
     * @param createBy 操作人id
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取操作人姓名
     *
     * @return create_name - 操作人姓名
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * 设置操作人姓名
     *
     * @param createName 操作人姓名
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
     * 获取操作信息
     *
     * @return request - 操作信息
     */
    public String getRequest() {
        return request;
    }

    /**
     * 设置操作信息
     *
     * @param request 操作信息
     */
    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
