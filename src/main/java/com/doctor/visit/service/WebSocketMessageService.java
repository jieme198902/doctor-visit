package com.doctor.visit.service;

import com.doctor.visit.domain.BusSocketMessage;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WebSocketMessageService {
    /**
     * 保存消息
     *
     * @param bus
     */
    void saveSocketMessage(BusSocketMessage bus);

    /**
     * 删除消息
     *
     * @param bus
     */
    void deleteSocketMessage(BusSocketMessage bus);

    /**
     * 获取消息列表
     *
     * @param bus
     * @param pageable
     */
    ComResponse<List<BusSocketMessage>> listSocketMessage(BusSocketMessage bus, Pageable pageable);
}
