package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusGoodsClass;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.vm.DoctorVisitWebSocketServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("聊天接口")
@RequestMapping(Constants.API_BASE_FRONT + "/websocket")
public class FrontWebSocketResource {


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
    @ApiOperation(value = "和谁聊天")
    public Object chartTo(String sid, String message) {
        return DoctorVisitWebSocketServer.sendMessage(sid, message) ? ComResponse.ok() : ComResponse.fail();
    }

}
