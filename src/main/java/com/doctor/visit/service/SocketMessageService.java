package com.doctor.visit.service;

import com.doctor.visit.domain.*;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;


public interface SocketMessageService {
    /**
     * 后台 - 查询聊天记录
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse listSocketMessage(BusSocketMessage bus, Pageable pageable);



    /**
     * 根据id删除聊天记录
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteSocketMessage(String ids);

}
