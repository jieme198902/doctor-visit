package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusPatient;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.domain.BusUserShippingAddress;
import com.doctor.visit.repository.BusUserShippingAddressMapper;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 用户地址管理
 */
@Service
public class UserShippingAddressService {
    private final CommonService commonService;
    private final BusUserShippingAddressMapper busUserShippingAddressMapper;

    public UserShippingAddressService(CommonService commonService, BusUserShippingAddressMapper busUserShippingAddressMapper) {
        this.commonService = commonService;
        this.busUserShippingAddressMapper = busUserShippingAddressMapper;
    }

    /**
     * 前台 - 获取用户地址列表
     *
     * @param busUserShippingAddress
     * @param pageable
     * @return
     */
    public ComResponse<List<BusUserShippingAddress>> listUserShippingAddress(BusUserShippingAddress busUserShippingAddress, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        busUserShippingAddress.setIsDel(Constants.EXIST);
        Example example = new Example(BusPatient.class);
        Example.Criteria criteria = example.createCriteria();

        //创建者
        if (null != busUserShippingAddress.getCreateBy()) {
            criteria.andEqualTo("createBy", busUserShippingAddress.getCreateBy());
        }

        Page<BusUserShippingAddress> busList = (Page<BusUserShippingAddress>) busUserShippingAddressMapper.selectByExample(example);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 前台 - 新增或者更新用户地址
     *
     * @param busUserShippingAddress
     * @param request
     * @return
     */
    public ComResponse<BusUserShippingAddress> insertOrUpdateUserShippingAddress(BusUserShippingAddress busUserShippingAddress, HttpServletRequest request) {
        if (null == busUserShippingAddress.getCreateBy()) {
            return ComResponse.failBadRequest();
        }
        BusUser busUser = commonService.getBusUser(busUserShippingAddress.getCreateBy());
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }
        busUserShippingAddress.setEditTime(new Date());
        busUserShippingAddress.setEditBy(busUser.getId());
        busUserShippingAddress.setEditName(busUser.getName());
        if (null != busUserShippingAddress.getId()) {
            busUserShippingAddressMapper.updateByPrimaryKeySelective(busUserShippingAddress);
        } else {
            busUserShippingAddress.setId(IDKeyUtil.generateId());
            busUserShippingAddress.setCreateTime(new Date());
            busUserShippingAddress.setCreateBy(busUser.getId());
            busUserShippingAddress.setCreateName(busUser.getName());
            busUserShippingAddressMapper.insertSelective(busUserShippingAddress);
        }

        return ComResponse.ok(busUserShippingAddress);
    }


    /**
     * 前台 - 根据id删除用户地址
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteUserShippingAddress(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusUserShippingAddress delRecord = new BusUserShippingAddress();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busUserShippingAddressMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }
}
