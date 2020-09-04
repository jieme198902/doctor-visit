package com.doctor.visit.service;

import com.doctor.visit.domain.BusDict;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DictService {
    /**
     * 字典列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusDict>> listDict(BusDict bus, Pageable pageable);

    /**
     * 新增或者更新字典
     *
     * @param bus
     * @param request
     * @return
     */
    ComResponse<BusDict> insertOrUpdateDict(BusDict bus, HttpServletRequest request);

    /**
     * 根据id删除字典
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteDict(String ids);
}
