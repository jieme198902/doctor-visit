package com.doctor.visit.web.rest.vm.front;


import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusFeedback;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.service.FeedbackService;
import com.doctor.visit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信用户的接口
 */
@RestController
@Api("微信用户的接口")
@RequestMapping(Constants.API_BASE_FRONT + "/user")
public class FrontUserResource {

    private final UserService userService;
    private final FeedbackService feedbackService;

    public FrontUserResource(UserService userService, FeedbackService feedbackService) {
        this.userService = userService;
        this.feedbackService = feedbackService;
    }

    /**
     * 登录接口
     *
     * @param jsCode
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusUser.class),
        @ApiImplicitParam(name = "jsCode",value = "jsCode"),
    })
    @PostMapping("authenticate")
    @ApiOperation(value = "登录接口")
    public Object authenticate(String jsCode, HttpServletRequest request) throws Exception {
        return userService.authenticate(jsCode, request);
    }


    /**
     * 前端 - 新增或者更新意见反馈
     *
     * @param bus
     * @param request
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusFeedback.class),
        @ApiImplicitParam(name = "token",value = "header中的token"),
        @ApiImplicitParam(name = "id",value = "不传是新增，传是更新"),
        @ApiImplicitParam(name = "mobile",value = "手机号"),
        @ApiImplicitParam(name = "content",value = "反馈信息"),
    })
    @PostMapping("insertOrUpdateFeedback")
    @ApiOperation(value = "前端 - 新增或者更新意见反馈")
    public Object insertOrUpdateFeedback(BusFeedback bus, HttpServletRequest request) throws Exception {
        return feedbackService.insertOrUpdateFeedback(bus, request);
    }

}
