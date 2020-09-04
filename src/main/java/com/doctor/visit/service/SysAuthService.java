package com.doctor.visit.service;

import com.doctor.visit.domain.*;
import com.doctor.visit.domain.dto.SysMenuDto;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SysAuthService {
    /**
     * 角色列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<SysRole>> listRole(SysRole bus, Pageable pageable);

    /**
     * 更新或者修改角色
     *
     * @param bus
     * @param request
     * @return
     */
    ComResponse<SysRole> insertOrUpdateRole(SysRole bus, HttpServletRequest request);

    /**
     * 根据id删除角色
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteRole(String ids);

    /**
     * 菜单列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<SysMenu>> listMenu(SysMenu bus, Pageable pageable);

    /**
     * 获取菜单树
     *
     * @param bus
     * @return
     */
    ComResponse<List<SysMenuDto>> listMenuTree(SysMenu bus);

    /**
     * 根据当前登录用户获取用户的按钮权限
     *
     * @param bus
     * @return
     */
    ComResponse<List<SysMenuDto>> listMenuTreeByState(SysMenu bus, String roleId);

    /**
     * 获取菜单树根据角色id
     *
     * @param bus
     * @return
     */
    ComResponse<List<String>> listMenuByRoleId(SysPermission bus);

    /**
     * 更新或者修改菜单
     *
     * @param bus
     * @param request
     * @return
     */
    ComResponse<SysMenu> insertOrUpdateMenu(SysMenu bus, HttpServletRequest request);

    /**
     * 根据id删除菜单
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteMenu(String ids);

    /**
     * 根据菜单id获取按钮列表
     *
     * @param sysMenu
     * @return
     */
    ComResponse<List<SysButton>> selectByMenu(SysMenu sysMenu);

    /**
     * 更新或者修改菜单
     *
     * @param bus
     * @param menus
     * @param permissions
     * @param request
     * @return
     */
    ComResponse<Integer> insertOrUpdatePermission(SysPermission bus, String menus, String permissions, HttpServletRequest request);

    /**
     * 修改用户的角色
     *
     * @param bus
     * @param roles   角色ids
     * @param request
     * @return
     */
    ComResponse<String> insertOrUpdateUserRole(SysRelationUserRole bus, String roles, HttpServletRequest request);

    /**
     * 按钮列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<SysButton>> listButton(SysButton bus, Pageable pageable);

    /**
     * 更新或者修改按钮
     *
     * @param bus
     * @param request
     * @return
     */
    ComResponse<SysButton> insertOrUpdateButton(SysButton bus, HttpServletRequest request);

    /**
     * 根据id删除角色
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteButton(String ids);

    /**
     * 更新或者修改菜单
     *
     * @param bus
     * @param buttons
     * @param request
     * @return
     */
    ComResponse<SysRelationMenuButton> insertOrUpdateRelationMenuButton(SysRelationMenuButton bus, String buttons, HttpServletRequest request);

    /**
     * 判断是否有权限访问
     *
     * @return
     */
    boolean hasPermission(HttpServletRequest request);
}
