package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusSocketMessage;
import com.doctor.visit.service.WebSocketMessageService;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.vm.DoctorVisitWebSocketServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@Api("聊天接口")
@RequestMapping(Constants.API_BASE_SYS + "/websocket")
public class SysWebSocketResource {
    @Autowired
    private WebSocketMessageService webSocketMessageService;

    /**
     * 聊天接口
     * @param request
     * @param fromUserId
     * @param fromUserName
     * @param toUserId
     * @param toUserName
     * @param message
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
        @ApiImplicitParam(name = "fromUserId", value = "发送者id",required = true),
        @ApiImplicitParam(name = "fromUserName", value = "发送者名字",required = true),
        @ApiImplicitParam(name = "toUserId", value = "接受者id",required = true),
        @ApiImplicitParam(name = "toUserName", value = "发送者名字",required = true),
        @ApiImplicitParam(name = "sysToClient", value = "是否是从后台发送到前端客户端：1是，0否",required = true),
        @ApiImplicitParam(name = "messageType", value = "消息类型：0文字；1图片；2语音；3视频",required = true),
        @ApiImplicitParam(name = "message", value = "聊天内容",required = true),
    })
    @PostMapping("chart")
    @ApiOperation(value = "聊天接口")
    public Object chart(HttpServletRequest request,String fromUserId,String fromUserName ,String toUserId,String toUserName,String sysToClient,String messageType, String message) throws Exception {
        if(StringUtils.isAnyBlank(toUserId,fromUserId,sysToClient,messageType,message)){
            return ComResponse.failBadRequest();
        }
        BusSocketMessage busSocketMessage = new BusSocketMessage();
        busSocketMessage.setId(IDKeyUtil.generateId());
        busSocketMessage.setIsDel(Constants.EXIST);
        busSocketMessage.setFromUserId(Long.valueOf(fromUserId));
        busSocketMessage.setFromUserName(fromUserName);
        busSocketMessage.setToUserId(Long.valueOf(toUserId));
        busSocketMessage.setToUserName(toUserName);
        busSocketMessage.setMessage(message);
        busSocketMessage.setSendTime(new Date());
        //是否是从后台发送到前端客户端：1是，0否
        busSocketMessage.setSysToClient(sysToClient);
        //消息类型：0文字；1图片；2语音；3视频
        busSocketMessage.setMessageType(messageType);
        DoctorVisitWebSocketServer webSocketServer = new DoctorVisitWebSocketServer();

        boolean succ = webSocketServer.sendMessage(toUserId, message) ;
        busSocketMessage.setSucc(succ?"1":"0");
        webSocketMessageService.saveSocketMessage(busSocketMessage);
        return succ? ComResponse.ok() : ComResponse.fail("发送失败");
    }

}
