package com.doctor.visit.domain;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "jhi_user_authority")
public class JhiUserAuthority implements Serializable {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "authority_name")
    private String authorityName;

    private static final long serialVersionUID = 1L;

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return authority_name
     */
    public String getAuthorityName() {
        return authorityName;
    }

    /**
     * @param authorityName
     */
    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }
}