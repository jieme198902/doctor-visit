package com.doctor.visit.service.impl;

import com.doctor.visit.domain.BusLog;
import com.doctor.visit.repository.BusLogMapper;
import com.doctor.visit.web.rest.util.ComResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 日志
 */
@Service
public class SysLogServiceImpl {

    private final BusLogMapper busLogMapper;

    public SysLogServiceImpl(BusLogMapper busLogMapper) {
        this.busLogMapper = busLogMapper;
    }

    /**
     * 日志列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusLog>> listLog(BusLog bus, Pageable pageable) {
        if (null != pageable) {
            PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(),"create_time desc");
        }
        BusLog record = new BusLog();
        if (StringUtils.isNotBlank(bus.getSys())) {
            record.setSys(bus.getSys());
        }
        if (StringUtils.isNotBlank(bus.getModel())) {
            record.setModel(bus.getModel());
        }
        if (StringUtils.isNotBlank(bus.getOperation())) {
            record.setOperation(bus.getOperation());
        }
        Page<BusLog> busList = (Page<BusLog>) busLogMapper.select(record);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }
}
