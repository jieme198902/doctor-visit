package com.doctor.visit.service;

import com.doctor.visit.domain.BusMessage;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    /**
     * 查询消息列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse listMessage(BusMessage bus, Pageable pageable);
    /**
     * 新增或者更新消息内容
     *
     * @param bus
     * @return
     */
    ComResponse<BusMessage> insertOrUpdateMessage(BusMessage bus);
    /**
     * 根据id删除消息
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteMessage(String ids);
    /**
     * 前台 - 删除我的消息
     *
     * @param ids
     * @return
     */
    ComResponse deleteMyMessage(String ids);
    /**
     * 前台 - 获取我的消息列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusMessage>> listMyMessage(BusMessage bus, Pageable pageable);
}
