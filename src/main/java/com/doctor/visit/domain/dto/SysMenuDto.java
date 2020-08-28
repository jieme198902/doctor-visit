package com.doctor.visit.domain.dto;

import com.doctor.visit.domain.SysButton;
import com.doctor.visit.domain.SysMenu;

import java.util.List;

public class SysMenuDto extends SysMenu {
    private String button;
    private List<SysButton> buttons;
    private List<SysMenuDto> children;

    public List<SysButton> getButtons() {
        return buttons;
    }

    public void setButtons(List<SysButton> buttons) {
        this.buttons = buttons;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public List<SysMenuDto> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenuDto> children) {
        this.children = children;
    }
}
