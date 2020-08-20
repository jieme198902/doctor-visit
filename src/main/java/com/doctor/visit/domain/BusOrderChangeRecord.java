package com.doctor.visit.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "bus_order_change_record")
public class BusOrderChangeRecord implements Serializable {
    @Id
    private Long id;

    /**
     * 业务表
     */
    private String bus;

    /**
     * 业务id
     */
    @Column(name = "bus_id")
    private Long busId;

    /**
     * 订单状态
     */
    @Column(name = "order_status")
    private String orderStatus;

    /**
     * 添加时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取业务表
     *
     * @return bus - 业务表
     */
    public String getBus() {
        return bus;
    }

    /**
     * 设置业务表
     *
     * @param bus 业务表
     */
    public void setBus(String bus) {
        this.bus = bus;
    }

    /**
     * 获取业务id
     *
     * @return bus_id - 业务id
     */
    public Long getBusId() {
        return busId;
    }

    /**
     * 设置业务id
     *
     * @param busId 业务id
     */
    public void setBusId(Long busId) {
        this.busId = busId;
    }

    /**
     * 获取订单状态
     *
     * @return order_status - 订单状态
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置订单状态
     *
     * @param orderStatus 订单状态
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取添加时间
     *
     * @return create_time - 添加时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置添加时间
     *
     * @param createTime 添加时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}