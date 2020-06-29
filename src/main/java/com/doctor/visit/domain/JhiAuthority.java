package com.doctor.visit.domain;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "jhi_authority")
public class JhiAuthority implements Serializable {
    @Id
    private String name;

    private static final long serialVersionUID = 1L;

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}