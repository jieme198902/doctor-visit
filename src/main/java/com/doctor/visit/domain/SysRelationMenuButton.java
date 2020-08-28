package com.doctor.visit.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sys_relation_menu_button")
public class SysRelationMenuButton implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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
     * 获取菜单id
     *
     * @return menu_id - 菜单id
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 设置菜单id
     *
     * @param menuId 菜单id
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取按钮id
     *
     * @return button_id - 按钮id
     */
    public Long getButtonId() {
        return buttonId;
    }

    /**
     * 设置按钮id
     *
     * @param buttonId 按钮id
     */
    public void setButtonId(Long buttonId) {
        this.buttonId = buttonId;
    }
}
