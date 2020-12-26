package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusSocketMessage;
import com.doctor.visit.service.WebSocketMessageService;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.doctor.visit.web.rest.vm.DoctorVisitWebSocketServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@Api("聊天接口")
@RequestMapping(Constants.API_BASE_FRONT + "/websocket")
public class FrontWebSocketResource {
    @Autowired
    private WebSocketMessageService webSocketMessageService;

    /**
     * 和谁聊天
     *
     * @param sid
     * @param message
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(name = "message", value = "聊天内容"),
        @ApiImplicitParam(name = "sid", value = "和谁聊天的用户id"),
    })
    @PostMapping("chartTo")
    @ApiOperation(value = "前端我和XXX的文字聊天")
    public Object chartTo(HttpServletRequest request,String sid, String message) throws Exception {
        BusSocketMessage busSocketMessage = new BusSocketMessage();
        busSocketMessage.setId(IDKeyUtil.generateId());
        busSocketMessage.setIsDel(Constants.EXIST);
        busSocketMessage.setFromUserId(Utils.getUserId(request));
        busSocketMessage.setToUserId(Long.valueOf(sid));
        busSocketMessage.setMessage(message);
        busSocketMessage.setSendTime(new Date());
        //是否是从后台发送到前端客户端：1是，0否
        busSocketMessage.setSysToClient("0");
        //消息类型：0文字；1图片；2语音；3视频
        busSocketMessage.setMessageType("0");
        boolean succ= DoctorVisitWebSocketServer.sendMessage(sid, message) ;
        busSocketMessage.setSucc(succ?"1":"0");
        webSocketMessageService.saveSocketMessage(busSocketMessage);
        return succ? ComResponse.ok() : ComResponse.fail();
    }

}
