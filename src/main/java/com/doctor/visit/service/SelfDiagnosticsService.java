package com.doctor.visit.service;

import com.doctor.visit.domain.BusSelfDiagnose;
import com.doctor.visit.domain.BusSelfDiagnosis;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SelfDiagnosticsService {
    /**
     * 获取自诊问题列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusSelfDiagnose>> listSelfDiagnose(BusSelfDiagnose bus, Pageable pageable);

    /**
     * 新增或者更新自诊问题
     *
     * @param bus
     * @param request
     * @return
     */
    ComResponse<BusSelfDiagnose> insertOrUpdateSelfDiagnose(BusSelfDiagnose bus, HttpServletRequest request);

    /**
     * 根据id删除自诊问题
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteSelfDiagnose(String ids);

    /**
     * 获取自诊结果列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusSelfDiagnosis>> listSelfDiagnosis(BusSelfDiagnosis bus, Pageable pageable);


    /**
     * 根据id获取问诊结果
     * @param bus
     * @return
     */
    ComResponse<BusSelfDiagnosis> oneGSelfDiagnosis(BusSelfDiagnosis bus);

    /**
     * 新增或者更新自诊结果
     *
     * @param bus
     * @param request
     * @return
     */
    ComResponse<BusSelfDiagnosis> insertOrUpdateSelfDiagnosis(BusSelfDiagnosis bus, HttpServletRequest request);

    /**
     * 根据id删除自诊结果
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteSelfDiagnosis(String ids);
}
