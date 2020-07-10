package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusEvaluate;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.repository.BusEvaluateMapper;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 我的业务层
 */
@Service
public class MineService {

    private final CommonService commonService;
    //
    private final BusEvaluateMapper busEvaluateMapper;

    public MineService(CommonService commonService, BusEvaluateMapper busEvaluateMapper) {
        this.commonService = commonService;
        this.busEvaluateMapper = busEvaluateMapper;
    }



    /**
     * 前台 - 新增或者更新评价
     * FIXME
     *
     * @param bus
     * @param request     这里需要处理文件
     * @return
     */
    public ComResponse<BusEvaluate> insertOrUpdateEvaluate(BusEvaluate bus, HttpServletRequest request) throws Exception {
        BusUser busUser = commonService.getBusUser(Utils.getUserId(request));
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }
        bus.setEditBy(busUser.getId());
        bus.setEditTime(new Date());
        bus.setEditName(busUser.getName());
        if (null != bus.getId()) {
            //修改评价
            busEvaluateMapper.updateByPrimaryKeySelective(bus);
        } else {
            //新增评价
            bus.setId(IDKeyUtil.generateId());
            bus.setCreateBy(busUser.getId());
            bus.setCreateTime(new Date());
            bus.setCreateName(busUser.getName());
            busEvaluateMapper.insertSelective(bus);
        }

        return ComResponse.ok(bus);
    }


    /**
     * 根据id删除评价
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteEvaluate(String ids) {
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
