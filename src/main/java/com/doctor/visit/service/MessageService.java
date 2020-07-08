package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.repository.*;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 消息的服务层
 *
 * @author kuanwang
 * @date 2020-06-29
 */
@Service
public class MessageService {

    private final CommonService commonService;
    //
    private final BusMessageMapper busMessageMapper;
    private final BusRelationMessageUserMapper busRelationMessageUserMapper;

    public MessageService(CommonService commonService, BusMessageMapper busMessageMapper, BusRelationMessageUserMapper busRelationMessageUserMapper) {
        this.commonService = commonService;
        this.busMessageMapper = busMessageMapper;
        this.busRelationMessageUserMapper = busRelationMessageUserMapper;
    }


    /**
     * 查询消息列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse listMessage(BusMessage bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        bus.setIsDel(Constants.EXIST);
        Page<BusMessage> busList = (Page<BusMessage>) busMessageMapper.select(bus);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 新增或者更新消息内容
     *
     * @param bus
     * @return
     */
    public ComResponse<BusMessage> insertOrUpdateMessage(BusMessage bus) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            bus.setEditTime(new Date());
            bus.setEditBy(jhiUser.getId());
            bus.setEditName(jhiUser.getFirstName());
            if (null != bus.getId()) {
                busMessageMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                busMessageMapper.insertSelective(bus);
            }
        } else {
            return ComResponse.failUnauthorized();
        }

        return ComResponse.ok(bus);
    }

    /**
     * 根据id删除消息
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteMessage(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusMessage delRecord = new BusMessage();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busMessageMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }


    /**
     * 前台 - 删除我的消息
     *
     * @param ids
     * @return
     */
    public ComResponse deleteMyMessage(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusRelationMessageUser delRecord = new BusRelationMessageUser();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busRelationMessageUserMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }


    /**
     * 前台 - 获取我的消息列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusMessage>> listMyMessage(BusMessage bus, Pageable pageable) {
        if (null == bus.getCreateBy()) {
            return ComResponse.failBadRequest();
        }
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Page<BusMessage> busList = (Page<BusMessage>) busMessageMapper.selectMessageByUserId(bus.getCreateBy());
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

}
