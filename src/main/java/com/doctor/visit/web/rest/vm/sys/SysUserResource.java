package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.domain.param.UserDoctorParam;
import com.doctor.visit.service.FeedbackService;
import com.doctor.visit.service.JhiUserService;
import com.doctor.visit.service.UserService;
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
    private final UserService userService;
    private final JhiUserService jhiUserService;

    public SysUserResource(FeedbackService feedbackService, UserService userService, JhiUserService jhiUserService) {
        this.feedbackService = feedbackService;
        this.userService = userService;
        this.jhiUserService = jhiUserService;
    }


    /**
     * 获取系统用户菜单列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysPermission.class)
    })
    @PostMapping("listSysUserMenu")
    @ApiOperation(value = "获取系统用户菜单列表")
    public Object listSysUserMenu(SysPermission bus) {
        return userService.listSysUserMenu(bus);
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

    /**
     * 获取微信用户列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusUser.class)
    })
    @PostMapping("listBusUser")
    @ApiOperation(value = "获取微信用户列表")
    public Object listBusUser(BusUser bus, Pageable pageable) {
        return userService.listBusUser(bus, pageable);
    }

    /**
     * 拉黑微信用户
     * @param ids
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = String.class)
    })
    @PostMapping("insertOrUpdateUserPullToBlacklist")
    @ApiOperation(value = "拉黑微信用户")
    public Object insertOrUpdateUserPullToBlacklist(String ids){
        return userService.insertOrUpdateUserPullToBlacklist(ids);
    }

    /**
     * 从黑名单中去掉微信用户
     * @param ids
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = String.class)
    })
    @PostMapping("insertOrUpdateUserPushFromBlacklist")
    @ApiOperation(value = "从黑名单中去掉微信用户")
    public Object insertOrUpdateUserPushFromBlacklist(String ids){
        return userService.insertOrUpdateUserPushFromBlacklist(ids);
    }

    /**
     * 获取系统用户列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = JhiUser.class)
    })
    @PostMapping("listJhiUser")
    @ApiOperation(value = "获取系统用户列表")
    public Object listJhiUser(JhiUser bus, Pageable pageable) {
        return jhiUserService.listJhiUser(bus, pageable);
    }

    /**
     * 获取系统用户列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = JhiUser.class)
    })
    @PostMapping("oneJhiUser")
    @ApiOperation(value = "获取系统用户信息")
    public Object oneJhiUser(JhiUser bus) {
        return jhiUserService.oneJhiUser(bus);
    }

    /**
     * 把用户id置为医生id
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = UserDoctorParam.class)
    })
    @PostMapping("updateUserIdToDoctorId")
    @ApiOperation(value = "把用户id置为医生id")
    public Object updateUserIdToDoctorId(UserDoctorParam bus){
        return jhiUserService.updateUserIdToDoctorId(bus);
    }
}
