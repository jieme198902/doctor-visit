package com.doctor.visit.service;

import com.doctor.visit.domain.BusFeedback;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FeedbackService {
    /**
     * 获取意见反馈列表
     *
     * @param bus
     * @param pageable
     * @return
     */

    ComResponse<List<BusFeedback>> listFeedback(BusFeedback bus, Pageable pageable);

    /**
     * 前端 - 新增或者更新意见反馈
     *
     * @param bus
     * @param request 这里需要处理文件
     * @return
     */
    ComResponse<BusFeedback> insertOrUpdateFeedback(BusFeedback bus, HttpServletRequest request) throws Exception;

    /**
     * 插入或者更新意见反馈的状态
     *
     * @param bus
     * @return
     * @throws Exception
     */
    ComResponse<BusFeedback> insertOrUpdateFeedbackState(BusFeedback bus) throws Exception;

    /**
     * 根据id删除意见反馈
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteFeedback(String ids);
}
