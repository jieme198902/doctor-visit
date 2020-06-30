package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusClincClass;
import com.doctor.visit.service.ClincClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信科室的接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@Api("微信科室的接口")
@RestController
@RequestMapping(Constants.API_BASE_SYS + "/clincClass")
public class SysClincClassResource {

    private final ClincClassService clincClassService;

    public SysClincClassResource(ClincClassService clincClassService) {
        this.clincClassService = clincClassService;
    }

    /**
     * 查询科室列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusClincClass.class)
    })
    @PostMapping("listClincClass")
    @ApiOperation(value = "查询科室列表")
    public Object listClincClass(BusClincClass bus, Pageable pageable) {
        return clincClassService.listClincClass(bus, pageable);
    }

    /**
     * 新增或者修改科室
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusClincClass.class)
    })
    @PostMapping("insertOrUpdateClincClass")
    @ApiOperation(value = "新增或者修改科室")
    public Object insertOrUpdateClincClass(BusClincClass bus, HttpServletRequest request) {
        return clincClassService.insertOrUpdateClincClass(bus, request);
    }

    /**
     * 根据id删除科室
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("deleteClincClass")
    @ApiOperation(value = "根据id删除科室")
    public Object deleteClincClass(String ids) {
        return clincClassService.deleteClincClass(ids);
    }
}
