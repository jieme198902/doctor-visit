package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.BusUser;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface BusUserMapper extends CommMapper<BusUser> {

    Integer countAllUser(@Param("thisMonth")String thisMonth);

    Map<String,String> findMineCount(@Param("userId")Long userId);
}
