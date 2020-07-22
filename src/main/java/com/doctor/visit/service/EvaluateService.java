package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusArticle;
import com.doctor.visit.domain.BusEvaluate;
import com.doctor.visit.repository.BusEvaluateMapper;
import com.doctor.visit.web.rest.util.ComResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kuanwang
 * 评价表
 */
@Service
public class EvaluateService {

    //
    private final BusEvaluateMapper busEvaluateMapper;

    public EvaluateService(BusEvaluateMapper busEvaluateMapper) {
        this.busEvaluateMapper = busEvaluateMapper;
    }

    /**
     * 后台 - 获取评价列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusEvaluate>> listEvaluate(BusEvaluate bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        bus.setIsDel(Constants.EXIST);

        Page<BusEvaluate> busEvaluateList = (Page<BusEvaluate>) busEvaluateMapper.select(bus);
        return ComResponse.ok(busEvaluateList.getResult(), busEvaluateList.getTotal());
    }

    /**
     * 后台 - 删除评价列表
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteEvaluate(String ids) {
        if (StringUtils.isBlank(ids)) {
            return ComResponse.fail("ids为空");
        }
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusEvaluate delRecord = new BusEvaluate();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busEvaluateMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }
}
