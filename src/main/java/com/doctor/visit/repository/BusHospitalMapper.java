package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.BusHospital;

import java.util.List;

public interface BusHospitalMapper extends CommMapper<BusHospital> {


    /**
     * 根据距离查询医院列表
     * @param busHospital
     * @return
     */
    List<BusHospital> selectHospitalByDistance(BusHospital busHospital);
}
