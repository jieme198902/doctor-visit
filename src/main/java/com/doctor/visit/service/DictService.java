package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.repository.BusDictMapper;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * 字典管理
 */
@Service
public class DictService {
    private final CommonService commonService;
    private final BusDictMapper busDictMapper;

    public DictService(CommonService commonService, BusDictMapper busDictMapper) {
        this.commonService = commonService;
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


    /**
     * 新增或者更新字典
     *
     * @param bus
     * @param request
     * @return
     */
    public ComResponse<BusDict> insertOrUpdateDict(BusDict bus, HttpServletRequest request) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }

            if (null != bus.getId()) {
                busDictMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());

                busDictMapper.insertSelective(bus);
            }
            return ComResponse.ok(bus);
        } else {
            return ComResponse.failUnauthorized();
        }
    }


    /**
     * 根据id删除字典
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteDict(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusDict delRecord = new BusDict();
            delRecord.setId(Long.parseLong(id));
            int i = busDictMapper.deleteByPrimaryKey(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

}
