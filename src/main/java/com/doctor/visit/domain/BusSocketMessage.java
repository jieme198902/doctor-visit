package com.doctor.visit.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "bus_socket_message")
public class BusSocketMessage implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 消息来自哪个用户
     */
    @Column(name = "from_user_name")
    private String fromUserName;
    /**
     * 消息来自哪个用户id
     */
    @Column(name = "from_user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fromUserId;

    /**
     * 消息发送到哪个用户id
     */
    @Column(name = "to_user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long toUserId;


    /**
     * 消息发送到哪个用户
     */
    @Column(name = "to_user_name")
    private String toUserName;

    /**
     * 是否是从后台发送到前端客户端：1是，0否
     */
    @Column(name = "sys_to_client")
    private String sysToClient;

    /**
     * 消息类型：0文字；1图片；2语音；3视频
     */
    @Column(name = "message_type")
    private String messageType;

    /**
     * 消息内容，文字的话直接存储文字，其他的存储url
     */
    private String message;
    /**
     * 是否发送成功，0否1是
     */
    private String succ;

    /**
     * 发送时间
     */
    @Column(name = "send_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")  //取日期时使用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//存日期时使用
    private Date sendTime;

    /**
     * 是否删除，1是0否
     */
    @Column(name = "is_del")
    private String isDel;

    private static final long serialVersionUID = 1L;

    public String getSucc() {
        return succ;
    }

    public void setSucc(String succ) {
        this.succ = succ;
    }

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

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    /**
     * 获取消息来自哪个用户
     *
     * @return from_user_id - 消息来自哪个用户
     */
    public Long getFromUserId() {
        return fromUserId;
    }

    /**
     * 设置消息来自哪个用户
     *
     * @param fromUserId 消息来自哪个用户
     */
    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    /**
     * 获取消息发送到哪个用户
     *
     * @return to_user_id - 消息发送到哪个用户
     */
    public Long getToUserId() {
        return toUserId;
    }

    /**
     * 设置消息发送到哪个用户
     *
     * @param toUserId 消息发送到哪个用户
     */
    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    /**
     * 获取是否是从后台发送到前端客户端：1是，0否
     *
     * @return sys_to_client - 是否是从后台发送到前端客户端：1是，0否
     */
    public String getSysToClient() {
        return sysToClient;
    }

    /**
     * 设置是否是从后台发送到前端客户端：1是，0否
     *
     * @param sysToClient 是否是从后台发送到前端客户端：1是，0否
     */
    public void setSysToClient(String sysToClient) {
        this.sysToClient = sysToClient;
    }

    /**
     * 获取消息类型：0文字；1图片；2语音；3视频
     *
     * @return message_type - 消息类型：0文字；1图片；2语音；3视频
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * 设置消息类型：0文字；1图片；2语音；3视频
     *
     * @param messageType 消息类型：0文字；1图片；2语音；3视频
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * 获取消息内容，文字的话直接存储文字，其他的存储url
     *
     * @return message - 消息内容，文字的话直接存储文字，其他的存储url
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置消息内容，文字的话直接存储文字，其他的存储url
     *
     * @param message 消息内容，文字的话直接存储文字，其他的存储url
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 发送时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 发送时间
     * @param sendTime
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
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
