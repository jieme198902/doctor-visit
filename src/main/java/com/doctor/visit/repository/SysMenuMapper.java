package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper extends CommMapper<SysMenu> {

    /**
     * 根据角色id获取该角色的菜单列表
     * @param roleId
     * @return
     */
    List<SysMenu> selectMenuByRoleId(@Param("roleId") Long roleId);

    /**
     *
     * @param sysMenu
     * @return
     */
    List<SysMenu> selectMenu(SysMenu sysMenu);

}
