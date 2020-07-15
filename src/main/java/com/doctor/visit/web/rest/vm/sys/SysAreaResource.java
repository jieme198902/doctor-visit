package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusArea;
import com.doctor.visit.domain.BusDict;
import com.doctor.visit.service.AreaService;
import com.doctor.visit.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取字典
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@RestController
@Api("地区接口")
@RequestMapping(Constants.API_BASE_SYS + "/area")
public class SysAreaResource {

    private final AreaService areaService;

    public SysAreaResource(AreaService areaService) {
        this.areaService = areaService;
    }


    /**
     * 查询地区列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusArea.class)
    })
    @PostMapping("listArea")
    @ApiOperation(value = "查询地区列表")
    public Object listArea(BusArea bus, Pageable pageable) {
        return areaService.listArea(bus, pageable);
    }

    /**
     * 查询无分页的地区列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusArea.class)
    })
    @PostMapping("listAreaNoPage")
    @ApiOperation(value = "查询无分页的地区列表")
    public Object listAreaNoPage(BusArea bus) {
        return areaService.listArea(bus, null);
    }

    /**
     * 查询无分页的地区列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusArea.class)
    })
    @PostMapping("listAreaForSelect")
    @ApiOperation(value = "查询无分页的地区列表")
    public Object listAreaForSelect(BusArea bus) {
        return areaService.listAreaForSelect(bus);
    }

}
