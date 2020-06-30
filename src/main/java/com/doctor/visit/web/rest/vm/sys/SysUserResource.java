package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusFeedback;
import com.doctor.visit.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户的接口
 */
@RestController
@Api("用户的接口")
@RequestMapping(Constants.API_BASE_SYS + "/user")
public class SysUserResource {

    private final FeedbackService feedbackService;

    public SysUserResource(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * 获取意见反馈列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusFeedback.class)
    })
    @PostMapping("listFeedback")
    @ApiOperation(value = "获取意见反馈列表")
    public Object listFeedback(BusFeedback bus, Pageable pageable) {
        return feedbackService.listFeedback(bus, pageable);
    }


    /**
     * 根据id删除意见反馈
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusFeedback.class)
    })
    @PostMapping("deleteFeedback")
    @ApiOperation(value = "根据id删除意见反馈")
    public Object deleteFeedback(String ids) {
        return feedbackService.deleteFeedback(ids);
    }
}
