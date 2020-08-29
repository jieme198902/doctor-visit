package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.SysButton;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysButtonMapper extends CommMapper<SysButton> {

    /**
     * 根据菜单id获取按钮列表
     * @param menuId
     * @return
     */
    List<SysButton> selectByMenuId(@Param("menuId") Long menuId);

    /**
     * 根据菜单id获取按钮列表
     * @param menuCode
     * @return
     */
    List<SysButton> selectByMenuCode(@Param("menuCode") String menuCode);
}
