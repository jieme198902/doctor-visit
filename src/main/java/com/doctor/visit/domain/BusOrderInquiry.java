package com.doctor.visit.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "bus_order_inquiry")
public class BusOrderInquiry implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 问诊方式：0电话，1图文，2视频咨询
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 医生id
     */
    @Column(name = "doctor_id")
    private Long doctorId;

    /**
     * 患者id
     */
    @Column(name = "patient_id")
    private Long patientId;

    /**
     * 问诊方式：0电话，1图文，2视频咨询
     */
    @Column(name = "ask_type")
    private String askType;

    /**
     * 价格
     */
    private Integer price;

    /**
     * 上限次数
     */
    @Column(name = "up_limit")
    private Integer upLimit;

    /**
     * 有效时间-分钟
     */
    @Column(name = "time_limit")
    private Integer timeLimit;

    /**
     * 订单状态：0已提交，待支付；1已支付，待接诊；2已支付，已接诊；4已评价；5已取消
     */
    @Column(name = "order_state")
    private String orderState;

    /**
     * 病情描述
     */
    @Column(name = "`condition`")
    private String condition;

    /**
     * 是否去医院就诊过：1是；0否
     */
    @Column(name = "see_doctor_yet")
    private String seeDoctorYet;

    /**
     * 本次患病时长：0一周内；1一月内；2半年内；3大于半年
     */
    @Column(name = "sick_time")
    private String sickTime;

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
     * 获取问诊方式：0电话，1图文，2视频咨询
     *
     * @return order_no - 问诊方式：0电话，1图文，2视频咨询
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置问诊方式：0电话，1图文，2视频咨询
     *
     * @param orderNo 问诊方式：0电话，1图文，2视频咨询
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取医生id
     *
     * @return doctor_id - 医生id
     */
    public Long getDoctorId() {
        return doctorId;
    }

    /**
     * 设置医生id
     *
     * @param doctorId 医生id
     */
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * 获取患者id
     *
     * @return patient_id - 患者id
     */
    public Long getPatientId() {
        return patientId;
    }

    /**
     * 设置患者id
     *
     * @param patientId 患者id
     */
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    /**
     * 获取问诊方式：0电话，1图文，2视频咨询
     *
     * @return ask_type - 问诊方式：0电话，1图文，2视频咨询
     */
    public String getAskType() {
        return askType;
    }

    /**
     * 设置问诊方式：0电话，1图文，2视频咨询
     *
     * @param askType 问诊方式：0电话，1图文，2视频咨询
     */
    public void setAskType(String askType) {
        this.askType = askType;
    }

    /**
     * 获取价格
     *
     * @return price - 价格
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price 价格
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 获取上限次数
     *
     * @return up_limit - 上限次数
     */
    public Integer getUpLimit() {
        return upLimit;
    }

    /**
     * 设置上限次数
     *
     * @param upLimit 上限次数
     */
    public void setUpLimit(Integer upLimit) {
        this.upLimit = upLimit;
    }

    /**
     * 获取有效时间-分钟
     *
     * @return time_limit - 有效时间-分钟
     */
    public Integer getTimeLimit() {
        return timeLimit;
    }

    /**
     * 设置有效时间-分钟
     *
     * @param timeLimit 有效时间-分钟
     */
    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    /**
     * 获取订单状态：0已提交，待支付；1已支付，待接诊；2已支付，已接诊；4已评价；5已取消
     *
     * @return order_state - 订单状态：0已提交，待支付；1已支付，待接诊；2已支付，已接诊；4已评价；5已取消
     */
    public String getOrderState() {
        return orderState;
    }

    /**
     * 设置订单状态：0已提交，待支付；1已支付，待接诊；2已支付，已接诊；4已评价；5已取消
     *
     * @param orderState 订单状态：0已提交，待支付；1已支付，待接诊；2已支付，已接诊；4已评价；5已取消
     */
    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    /**
     * 获取病情描述
     *
     * @return condition - 病情描述
     */
    public String getCondition() {
        return condition;
    }

    /**
     * 设置病情描述
     *
     * @param condition 病情描述
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * 获取是否去医院就诊过：1是；0否
     *
     * @return see_doctor_yet - 是否去医院就诊过：1是；0否
     */
    public String getSeeDoctorYet() {
        return seeDoctorYet;
    }

    /**
     * 设置是否去医院就诊过：1是；0否
     *
     * @param seeDoctorYet 是否去医院就诊过：1是；0否
     */
    public void setSeeDoctorYet(String seeDoctorYet) {
        this.seeDoctorYet = seeDoctorYet;
    }

    /**
     * 获取本次患病时长：0一周内；1一月内；2半年内；3大于半年
     *
     * @return sick_time - 本次患病时长：0一周内；1一月内；2半年内；3大于半年
     */
    public String getSickTime() {
        return sickTime;
    }

    /**
     * 设置本次患病时长：0一周内；1一月内；2半年内；3大于半年
     *
     * @param sickTime 本次患病时长：0一周内；1一月内；2半年内；3大于半年
     */
    public void setSickTime(String sickTime) {
        this.sickTime = sickTime;
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
