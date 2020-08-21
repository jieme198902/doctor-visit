package com.doctor.visit.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "bus_order_goods")
public class BusOrderGoods implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商品id
     */
    @Column(name = "goods_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;

    /**
     * 商品大订单id
     */
    @Column(name = "order_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    /**
     * 市场单价
     */
    @Column(name = "market_price")
    private Integer marketPrice;

    /**
     * 单价
     */
    private Integer price;

    /**
     * 购买数量
     */
    private Integer num;

    /**
     * 小订单金额=单价x购买数量
     */
    @Column(name = "total_amount")
    private Integer totalAmount;

    /**
     * 下单备注
     */
    private String remark;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")  //取日期时使用
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
     * 获取商品id
     *
     * @return goods_id - 商品id
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品id
     *
     * @param goodsId 商品id
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取商品大订单id
     *
     * @return order_id - 商品大订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置商品大订单id
     *
     * @param orderId 商品大订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取市场单价
     *
     * @return market_price - 市场单价
     */
    public Integer getMarketPrice() {
        return marketPrice;
    }

    /**
     * 设置市场单价
     *
     * @param marketPrice 市场单价
     */
    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * 获取单价
     *
     * @return price - 单价
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 设置单价
     *
     * @param price 单价
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 获取购买数量
     *
     * @return num - 购买数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置购买数量
     *
     * @param num 购买数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取小订单金额=单价x购买数量
     *
     * @return total_amount - 小订单金额=单价x购买数量
     */
    public Integer getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置小订单金额=单价x购买数量
     *
     * @param totalAmount 小订单金额=单价x购买数量
     */
    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 获取下单备注
     *
     * @return remark - 下单备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置下单备注
     *
     * @param remark 下单备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
