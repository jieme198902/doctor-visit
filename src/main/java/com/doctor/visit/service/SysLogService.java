package com.doctor.visit.service;

import com.doctor.visit.domain.BusLog;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SysLogService {
    /**
     * 日志列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusLog>> listLog(BusLog bus, Pageable pageable);
}
