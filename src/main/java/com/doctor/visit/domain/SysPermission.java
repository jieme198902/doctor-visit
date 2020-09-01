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
     * 菜单id
     */
    @Column(name = "menu_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long menuId;


    /**
     * 按钮id
     */
    @Column(name = "button_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long buttonId;

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
     * 菜单id
     * @return
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 菜单id
     * @param menuId
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * 按钮id
     * @return
     */
    public Long getButtonId() {
        return buttonId;
    }

    /**
     * 按钮id
     * @param buttonId
     */
    public void setButtonId(Long buttonId) {
        this.buttonId = buttonId;
    }
}
