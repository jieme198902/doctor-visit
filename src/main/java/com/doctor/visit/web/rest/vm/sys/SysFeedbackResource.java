package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusFeedback;
import com.doctor.visit.service.impl.FeedbackServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 意见反馈的接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@Api("意见反馈的接口")
@RestController
@RequestMapping(Constants.API_BASE_SYS + "/feedback")
public class SysFeedbackResource {

    private final FeedbackServiceImpl feedbackService;

    public SysFeedbackResource(FeedbackServiceImpl feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * 查询意见反馈列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusFeedback.class)
    })
    @PostMapping("listFeedback")
    @ApiOperation(value = "查询意见反馈列表")
    public Object listFeedback(BusFeedback bus, Pageable pageable) {
        return feedbackService.listFeedback(bus, pageable);
    }

    /**
     * 插入或者更新意见反馈的状态
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusFeedback.class)
    })
    @PostMapping("insertOrUpdateFeedbackState")
    @ApiOperation(value = "插入或者更新意见反馈的状态")
    public Object insertOrUpdateFeedbackState(BusFeedback bus) throws Exception {
        return feedbackService.insertOrUpdateFeedbackState(bus);
    }

}
