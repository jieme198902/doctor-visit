package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper extends CommMapper<SysRole> {

    List<SysRole> selectUserRole(@Param("userId") Long userId);
}
