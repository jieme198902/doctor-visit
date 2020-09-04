package com.doctor.visit.service.impl;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusArea;
import com.doctor.visit.domain.BusDict;
import com.doctor.visit.repository.BusAreaMapper;
import com.doctor.visit.web.rest.util.ComResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 地区管理
 */
@Service
public class AreaServiceImpl {

    private final BusAreaMapper busAreaMapper;

    public AreaServiceImpl(BusAreaMapper busAreaMapper) {
        this.busAreaMapper = busAreaMapper;
    }

    /**
     * 新增或者修改地区
     * @param bus
     * @return
     */
    public ComResponse<BusArea> insertOrUpdateArea(BusArea bus) {
        int count = 0;
        if (StringUtils.isNotBlank(bus.getWgbm())) {
            count = busAreaMapper.updateByPrimaryKeySelective(bus);
        } else {
            count = busAreaMapper.insertSelective(bus);
        }
        return 1 == count ? ComResponse.ok(bus) : ComResponse.fail();
    }

    /**
     * 删除地区
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteArea(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusArea delRecord = new BusArea();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setWgbm(id);
            int i = busAreaMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

    /**
     * 地区列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusArea>> listArea(BusArea bus, Pageable pageable) {
        if (null != pageable) {
            PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        }
        BusArea record = new BusArea();
        if (StringUtils.isNotBlank(bus.getSjwgbm())) {
            record.setSjwgbm(bus.getSjwgbm());
        }
        if (StringUtils.isNotBlank(bus.getWgbm())) {
            record.setWgbm(bus.getWgbm());
        }
        if (StringUtils.isNotBlank(bus.getWgmc())) {
            record.setWgmc(bus.getWgmc());
        }
        record.setIsDel(Constants.EXIST);
        if (null != pageable) {
            Page<BusArea> busList = (Page<BusArea>) busAreaMapper.select(record);
            return ComResponse.ok(busList.getResult(), busList.getTotal());
        } else {
            return ComResponse.ok(busAreaMapper.select(record));
        }
    }

    /**
     * 查询无分页的地区列表
     *
     * @param bus
     * @return
     */
    public ComResponse<List<Map<String, Object>>> listAreaForSelect(BusArea bus) {
        List<Map<String, Object>> areas = Constants.areaMap.get(bus.getSjwgbm() + bus.getWgjb());
        if (null != areas && !areas.isEmpty()) {
            return ComResponse.ok(areas);
        }
        Long wgjb = bus.getWgjb();
        List<Map<String, Object>> busAreas = busAreaMapper.selectAreaForSelect(bus.getSjwgbm());
        if (wgjb >= 1) {
            if (wgjb >= 2) {
                for (Map<String, Object> area : busAreas) {
                    List<Map<String, Object>> secondAreas = busAreaMapper.selectAreaForSelect(String.valueOf(area.get("value")));
                    area.put("children", secondAreas);
                    if (wgjb >= 3) {
                        for (Map<String, Object> secondArea : secondAreas) {
                            List<Map<String, Object>> thirdAreas = busAreaMapper.selectAreaForSelect(String.valueOf(secondArea.get("value")));
                            secondArea.put("children", thirdAreas);
                        }
                    }
                }
            }
        }
        Constants.areaMap.put(bus.getSjwgbm() + bus.getWgjb(), busAreas, 21600, TimeUnit.SECONDS);
        return ComResponse.ok(busAreas);
    }

}
