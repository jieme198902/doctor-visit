package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusSocketMessage;
import com.doctor.visit.domain.BusUserShoppingCart;
import com.doctor.visit.repository.BusSocketMessageMapper;
import com.doctor.visit.web.rest.util.ComResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 维护websocket消息的类
 *
 * @author kuanwang
 */
@Service
@EnableAsync
public class WebSocketMessageService {

    private final BusSocketMessageMapper busSocketMessageMapper;


    public WebSocketMessageService(BusSocketMessageMapper busSocketMessageMapper) {
        this.busSocketMessageMapper = busSocketMessageMapper;
    }

    /**
     * 保存消息
     *
     * @param bus
     */
    @Async
    public void saveSocketMessage(BusSocketMessage bus) {
        bus.setSendTime(new Date());
        busSocketMessageMapper.insertSelective(bus);
    }

    /**
     * 删除消息
     *
     * @param bus
     */
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
    public ComResponse<List<BusSocketMessage>> listSocketMessage(BusSocketMessage bus, Pageable pageable) {
        bus.setIsDel(Constants.EXIST);
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Page<BusSocketMessage> busList = (Page<BusSocketMessage>) busSocketMessageMapper.select(bus);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }


}
