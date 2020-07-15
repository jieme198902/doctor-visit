package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.BusArea;

import java.util.List;
import java.util.Map;

public interface BusAreaMapper extends CommMapper<BusArea> {

    /**
     *
     * @param wgbm
     * @return
     */
    List<Map<String,Object>> selectAreaForSelect(String wgbm);

}
