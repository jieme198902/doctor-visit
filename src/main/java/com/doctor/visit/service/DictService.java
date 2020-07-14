package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusDict;
import com.doctor.visit.domain.BusDoctor;
import com.doctor.visit.repository.BusDictMapper;
import com.doctor.visit.web.rest.util.ComResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典管理
 */
@Service
public class DictService {
    private final BusDictMapper busDictMapper;

    public DictService(BusDictMapper busDictMapper) {
        this.busDictMapper = busDictMapper;
    }

    /**
     * 字典列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusDict>> listDict(BusDict bus, Pageable pageable) {
        if (null != pageable) {
            PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        }
        BusDict record = new BusDict();
        if (StringUtils.isNotBlank(bus.getDicName())) {
            record.setDicName(bus.getDicName());
        }
        if (StringUtils.isNotBlank(bus.getDicType())) {
            record.setDicType(bus.getDicType());
        }
        if (StringUtils.isNotBlank(bus.getDicValue())) {
            record.setDicValue(bus.getDicValue());
        }
        if (StringUtils.isNotBlank(bus.getEnable())) {
            record.setEnable(bus.getEnable());
        }
        if (null != bus.getPid()) {
            record.setPid(bus.getPid());
        }
        if (null != pageable) {
            Page<BusDict> busList = (Page<BusDict>) busDictMapper.select(record);
            return ComResponse.ok(busList.getResult(), busList.getTotal());
        } else {
            return ComResponse.ok(busDictMapper.select(record));
        }
    }


}
