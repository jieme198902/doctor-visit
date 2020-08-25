package com.doctor.visit.domain.dto;

import com.doctor.visit.domain.SysMenu;

import java.util.List;

public class SysMenuDto extends SysMenu {
    private List<SysMenu> children;

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }
}
