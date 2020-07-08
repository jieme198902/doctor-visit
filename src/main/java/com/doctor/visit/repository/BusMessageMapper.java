package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.BusMessage;

import java.util.List;

public interface BusMessageMapper extends CommMapper<BusMessage> {
    /**
     * 获取用户的消息列表
     * @param userId
     * @return
     */
    List<BusMessage> selectMessageByUserId(Long userId);
}
