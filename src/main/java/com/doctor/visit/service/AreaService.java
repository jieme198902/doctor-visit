package com.doctor.visit.service;

import com.doctor.visit.domain.BusArea;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface AreaService {
    /**
     * 新增或者修改地区
     * @param bus
     * @return
     */
    ComResponse<BusArea> insertOrUpdateArea(BusArea bus);
    /**
     * 删除地区
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteArea(String ids);
    /**
     * 地区列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusArea>> listArea(BusArea bus, Pageable pageable);
    /**
     * 查询无分页的地区列表
     *
     * @param bus
     * @return
     */
    ComResponse<List<Map<String, Object>>> listAreaForSelect(BusArea bus);
}
