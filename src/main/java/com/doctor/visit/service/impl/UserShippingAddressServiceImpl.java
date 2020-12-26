package com.doctor.visit.service.impl;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusPatient;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.domain.BusUserShippingAddress;
import com.doctor.visit.repository.BusUserShippingAddressMapper;
import com.doctor.visit.service.common.CommonService;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
public class UserShippingAddressServiceImpl implements com.doctor.visit.service.UserShippingAddressService {
    private final CommonService commonService;
    private final BusUserShippingAddressMapper busUserShippingAddressMapper;

    public UserShippingAddressServiceImpl(CommonService commonService, BusUserShippingAddressMapper busUserShippingAddressMapper) {
        this.commonService = commonService;
        this.busUserShippingAddressMapper = busUserShippingAddressMapper;
    }

    /**
     * 前台 - 获取用户地址列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @Override
    public ComResponse<List<BusUserShippingAddress>> listUserShippingAddress(BusUserShippingAddress bus, Pageable pageable, HttpServletRequest request) throws Exception {
        BusUser busUser = commonService.getBusUser(Utils.getUserId(request));
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }
        bus.setCreateBy(busUser.getId());

        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Example example = new Example(BusPatient.class);
        Example.Criteria criteria = example.createCriteria();

        //创建者
        criteria.andEqualTo("createBy", bus.getCreateBy());
        criteria.andEqualTo("isDel",Constants.EXIST);

        Page<BusUserShippingAddress> busList = (Page<BusUserShippingAddress>) busUserShippingAddressMapper.selectByExample(example);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 前台 - 新增或者更新用户地址
     *
     * @param bus
     * @param request
     * @return
     */
    @Override
    public ComResponse<BusUserShippingAddress> insertOrUpdateUserShippingAddress(BusUserShippingAddress bus, HttpServletRequest request) throws Exception {
        BusUser busUser = commonService.getBusUser(Utils.getUserId(request));
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }
        bus.setEditTime(new Date());
        bus.setEditBy(busUser.getId());
        bus.setEditName(busUser.getName());
        if (null != bus.getId()) {
            busUserShippingAddressMapper.updateByPrimaryKeySelective(bus);
        } else {
            bus.setId(IDKeyUtil.generateId());
            bus.setCreateTime(new Date());
            bus.setCreateBy(busUser.getId());
            bus.setCreateName(busUser.getName());
            busUserShippingAddressMapper.insertSelective(bus);
        }

        return ComResponse.ok(bus);
    }


    /**
     * 前台 - 根据id删除用户地址
     *
     * @param ids
     * @return
     */
    @Override
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
