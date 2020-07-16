package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusDict;
import com.doctor.visit.domain.BusHospital;
import com.doctor.visit.service.DictService;
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
 * 获取字典
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@RestController
@Api("字典接口")
@RequestMapping(Constants.API_BASE_SYS + "/dict")
public class SysDictResource {

    private final DictService dictService;

    public SysDictResource(DictService dictService) {
        this.dictService = dictService;
    }


    /**
     * 查询字典列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusDict.class)
    })
    @PostMapping("listDict")
    @ApiOperation(value = "查询字典列表")
    public Object listDict(BusDict bus, Pageable pageable) {
        return dictService.listDict(bus, pageable);
    }

    /**
     * 查询无分页的字典列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusDict.class)
    })
    @PostMapping("listDictNoPage")
    @ApiOperation(value = "查询无分页的字典列表")
    public Object listDictNoPage(BusDict bus) {
        return dictService.listDict(bus, null);
    }


    /**
     * 新增或者修改字典
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusDict.class)
    })
    @PostMapping("insertOrUpdateDict")
    @ApiOperation(value = "新增或者修改字典")
    public Object insertOrUpdateHospital(BusDict bus, HttpServletRequest request) {
        return dictService.insertOrUpdateDict(bus,request);
    }

    /**
     * 根据id删除字典
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("deleteDict")
    @ApiOperation(value = "根据id删除字典")
    public Object deleteDict(String ids) {
        return dictService.deleteDict(ids);
    }
}
