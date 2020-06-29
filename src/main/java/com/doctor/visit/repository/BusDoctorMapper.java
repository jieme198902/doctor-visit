package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.BusDoctor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusDoctorMapper extends CommMapper<BusDoctor> {

    /**
     *
     * @param userId
     * @return
     */
    List<BusDoctor> selectFavDoctor(@Param("userId") Long userId);

}
