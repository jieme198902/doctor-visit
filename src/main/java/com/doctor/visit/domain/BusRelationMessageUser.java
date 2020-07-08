package com.doctor.visit.domain;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "bus_relation_message_user")
public class BusRelationMessageUser implements Serializable {
    @Id
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 消息id
     */
    @Column(name = "message_id")
    private Long messageId;

    /**
     * 是否删除，1是，0否
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
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取消息id
     *
     * @return message_id - 消息id
     */
    public Long getMessageId() {
        return messageId;
    }

    /**
     * 设置消息id
     *
     * @param messageId 消息id
     */
    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    /**
     * 获取是否删除，1是，0否
     *
     * @return is_del - 是否删除，1是，0否
     */
    public String getIsDel() {
        return isDel;
    }

    /**
     * 设置是否删除，1是，0否
     *
     * @param isDel 是否删除，1是，0否
     */
    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }
}