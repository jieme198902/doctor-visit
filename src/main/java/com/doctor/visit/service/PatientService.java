package com.doctor.visit.service;

import com.doctor.visit.domain.BusPatient;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PatientService {
    /**
     * 前台 - 获取患者列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusPatient>> listPatient(BusPatient bus, Pageable pageable, HttpServletRequest request, boolean sys) throws Exception;

    /**
     * 前台 - 新增或者更新患者
     *
     * @param bus
     * @param request
     * @return
     */
    ComResponse<BusPatient> insertOrUpdatePatient(BusPatient bus, HttpServletRequest request) throws Exception;

    /**
     * 前台 - 根据id删除患者
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deletePatient(String ids);
}
