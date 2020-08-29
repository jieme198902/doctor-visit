package com.doctor.visit.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sys_permission")
public class SysPermission implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    /**
     * 菜单id或者按钮id
     */
    @Column(name = "menu_button_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long menuButtonId;


    /**
     * 菜单或者按钮：0菜单；1按钮
     */
    @Column(name = "menu_button_type")
    private String menuButtonType;

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
     * 获取角色id
     *
     * @return role_id - 角色id
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 菜单id或者按钮id
     * @return
     */
    public Long getMenuButtonId() {
        return menuButtonId;
    }

    /**
     * 菜单id或者按钮id
     * @param menuButtonId
     */
    public void setMenuButtonId(Long menuButtonId) {
        this.menuButtonId = menuButtonId;
    }

    /**
     * 菜单或者按钮：0菜单；1按钮
     * @return
     */
    public String getMenuButtonType() {
        return menuButtonType;
    }

    /**
     * 菜单或者按钮：0菜单；1按钮
     * @param menuButtonType
     */
    public void setMenuButtonType(String menuButtonType) {
        this.menuButtonType = menuButtonType;
    }
}
