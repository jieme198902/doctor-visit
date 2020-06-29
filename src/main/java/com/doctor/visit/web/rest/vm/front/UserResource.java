package com.doctor.visit.web.rest.vm.front;


import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录接口
     * @param jsCode
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusUser.class)
    })
    @PostMapping("authenticate")
    @ApiOperation(value = "登录接口")
    public Object authenticate(String jsCode, HttpServletRequest request) {
        return userService.authenticate(jsCode, request);
    }
}
