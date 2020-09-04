package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.service.ClincClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 科室接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@Api("科室接口")
@RestController
@RequestMapping(Constants.API_BASE_FRONT + "/clincClass")
public class FrontClincClassResource {
    private final ClincClassService clincClassService;

    public FrontClincClassResource(ClincClassService clincClassService) {
        this.clincClassService = clincClassService;
    }


    /**
     * 查询科室列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusClincClass.class),
        @ApiImplicitParam(name = "clincClassName", value = "科室名称"),
    })
    @PostMapping("listClincClass")
    @ApiOperation(value = "查询科室列表")
    public Object listClincClass(BusClincClass bus, Pageable pageable) {
        return clincClassService.listClincClass(bus, pageable);
    }

}
