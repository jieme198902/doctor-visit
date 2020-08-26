package com.doctor.visit.domain.dto;

import com.doctor.visit.domain.SysMenu;

import java.util.List;

public class SysMenuDto extends SysMenu {
    private List<SysMenuDto> children;

    public List<SysMenuDto> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenuDto> children) {
        this.children = children;
    }
}
