package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端 我的接口
 */
@RestController
@Api("微信我的的接口")
@RequestMapping(Constants.API_BASE_FRONT + "/mine")
public class MineResource {


}
