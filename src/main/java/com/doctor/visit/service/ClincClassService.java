package com.doctor.visit.service;

import com.doctor.visit.domain.BusClincClass;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ClincClassService {
    /**
     * 获取科室列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusClincClass>> listClincClass(BusClincClass bus, Pageable pageable);

    /**
     * 新增或者更新科室
     *
     * @param bus
     * @param request
     * @return
     */
    ComResponse<BusClincClass> insertOrUpdateClincClass(BusClincClass bus, HttpServletRequest request);

    /**
     * 根据id删除科室
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteClincClass(String ids);
}
