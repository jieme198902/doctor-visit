package com.doctor.visit.domain.dto;

import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.domain.SysRole;

import java.util.List;

/**
 * 获取用户的角色列表
 */
public class JhiUserDto extends JhiUser {
    private List<SysRole> sysRoles;

    public List<SysRole> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(List<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }
}
