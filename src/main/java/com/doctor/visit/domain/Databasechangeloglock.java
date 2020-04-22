package com.doctor.visit.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Databasechangeloglock implements Serializable {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LOCKED")
    private Boolean locked;

    @Column(name = "LOCKGRANTED")
    private Date lockgranted;

    @Column(name = "LOCKEDBY")
    private String lockedby;

    private static final long serialVersionUID = 1L;

    /**
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return LOCKED
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * @param locked
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * @return LOCKGRANTED
     */
    public Date getLockgranted() {
        return lockgranted;
    }

    /**
     * @param lockgranted
     */
    public void setLockgranted(Date lockgranted) {
        this.lockgranted = lockgranted;
    }

    /**
     * @return LOCKEDBY
     */
    public String getLockedby() {
        return lockedby;
    }

    /**
     * @param lockedby
     */
    public void setLockedby(String lockedby) {
        this.lockedby = lockedby;
    }
}