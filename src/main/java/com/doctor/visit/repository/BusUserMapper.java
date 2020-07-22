package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.BusUser;
import org.apache.ibatis.annotations.Param;

public interface BusUserMapper extends CommMapper<BusUser> {

    Integer countAllUser(@Param("thisMonth")String thisMonth);
}
