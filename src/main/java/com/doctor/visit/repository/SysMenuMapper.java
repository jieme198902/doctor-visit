package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.SysMenu;
import com.doctor.visit.domain.SysPermission;

import java.util.List;

public interface SysMenuMapper extends CommMapper<SysMenu> {


    List<SysMenu> selectMenuByRoleId(SysPermission sysPermission);
}
