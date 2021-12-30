package com.doctor.visit.service.impl;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusSocketMessage;
import com.doctor.visit.repository.BusSocketMessageMapper;
import com.doctor.visit.service.WebSocketMessageService;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 维护websocket消息的类
 *
 * @author kuanwang
 */
@Service
public class WebSocketMessageServiceImpl implements WebSocketMessageService {

    private final BusSocketMessageMapper busSocketMessageMapper;


    public WebSocketMessageServiceImpl(BusSocketMessageMapper busSocketMessageMapper) {
        this.busSocketMessageMapper = busSocketMessageMapper;
    }

    /**
     * 保存消息
     * CREATE TABLE `bus_socket_message` (
     *   `id` bigint(20) NOT NULL,
     *   `from_user_id` bigint(20) NOT NULL COMMENT '消息来自哪个用户id',
     *   `to_user_id` bigint(20) NOT NULL COMMENT '消息发送到哪个用户id',
     *   `to_user_name` varchar(255) DEFAULT NULL COMMENT '消息发送到哪个用户',
     *   `from_user_name` varchar(255) DEFAULT NULL COMMENT '消息来自哪个用户',
     *   `sys_to_client` char(1) NOT NULL COMMENT '是否是从后台发送到前端客户端：1是，0否',
     *   `message_type` char(1) NOT NULL COMMENT '消息类型：0文字；1图片；2语音；3视频',
     *   `message` varchar(255) DEFAULT NULL COMMENT '消息内容，文字的话直接存储文字，其他的存储url',
     *   `send_time` datetime NOT NULL COMMENT '发送时间',
     *   `is_del` char(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是0否',
     *   `succ` char(1) NOT NULL COMMENT '是否发送成功，1是0否',
     *   PRIMARY KEY (`id`)
     * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='socket消息表';
     *
     * @param bus
     */
    @Override
    public void saveSocketMessage(BusSocketMessage bus) {
        bus.setSendTime(new Date());
        busSocketMessageMapper.insertSelective(bus);
    }

    /**
     * 删除消息
     *
     * @param bus
     */
    @Override
    @Async
    public void deleteSocketMessage(BusSocketMessage bus) {
        bus.setIsDel(Constants.DELETE);
        busSocketMessageMapper.updateByPrimaryKeySelective(bus);
    }

    /**
     * 获取消息列表
     *
     * @param bus
     * @param pageable
     */
    @Override
    public ComResponse<List<BusSocketMessage>> listSocketMessage(BusSocketMessage bus, Pageable pageable) {
        bus.setIsDel(Constants.EXIST);
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Page<BusSocketMessage> busList = (Page<BusSocketMessage>) busSocketMessageMapper.select(bus);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }


}
