package com.doctor.visit.service;

import com.doctor.visit.domain.BusHospital;
import com.doctor.visit.domain.dto.BusHospitalDto;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface HospitalService {
    /**
     * 前台 - 获取医院列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusHospitalDto>> listHospital(BusHospital bus, Pageable pageable);

    /**
     * 新增或者更新医院
     *
     * @param bus
     * @param request 这里需要处理文件
     * @return
     */
    ComResponse<BusHospital> insertOrUpdateHospital(BusHospital bus, HttpServletRequest request);

    /**
     * 根据id删除医院
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteHospital(String ids);
}
