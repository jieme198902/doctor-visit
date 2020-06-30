package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusEvaluate;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.repository.BusEvaluateMapper;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
     * 后台 - 获取评价列表
     *
     * @param busEvaluate
     * @param pageable
     * @return
     */
    public ComResponse<List<BusEvaluate>> listEvaluate(BusEvaluate busEvaluate, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        busEvaluate.setIsDel(Constants.EXIST);

        Page<BusEvaluate> busEvaluateList = (Page<BusEvaluate>) busEvaluateMapper.select(busEvaluate);
        return ComResponse.ok(busEvaluateList.getResult(), busEvaluateList.getTotal());
    }

    /**
     * 前台 - 新增或者更新评价
     * FIXME
     *
     * @param busEvaluate
     * @param request     这里需要处理文件
     * @return
     */
    public ComResponse<BusEvaluate> insertOrUpdateEvaluate(BusEvaluate busEvaluate, HttpServletRequest request) {
        BusUser busUser = commonService.getBusUser(busEvaluate.getCreateBy());
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }
        busEvaluate.setEditBy(busUser.getId());
        busEvaluate.setEditTime(new Date());
        busEvaluate.setEditName(busUser.getName());
        if (null != busEvaluate.getId()) {
            //修改评价
            busEvaluateMapper.updateByPrimaryKeySelective(busEvaluate);
        } else {
            //新增评价
            busEvaluate.setId(IDKeyUtil.generateId());
            busEvaluate.setCreateBy(busUser.getId());
            busEvaluate.setCreateTime(new Date());
            busEvaluate.setCreateName(busUser.getName());
            busEvaluateMapper.insertSelective(busEvaluate);
        }

        return ComResponse.ok(busEvaluate);
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
