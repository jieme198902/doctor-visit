package com.doctor.visit.service.impl;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusClincClass;
import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.repository.BusClincClassMapper;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.service.common.CommonService;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 科室的业务层
 *
 * @author kuanwang
 * @date 2020-06-29
 */
@Service
public class ClincClassServiceImpl implements com.doctor.visit.service.ClincClassService {
    private final CommonService commonService;
    //
    private final BusClincClassMapper busClincClassMapper;

    public ClincClassServiceImpl(CommonService commonService, BusClincClassMapper busClincClassMapper) {
        this.commonService = commonService;
        this.busClincClassMapper = busClincClassMapper;
    }

    /**
     * 获取科室列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @Override
    public ComResponse<List<BusClincClass>> listClincClass(BusClincClass bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());

        Example example = new Example(BusClincClass.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("isDel", Constants.EXIST);
        if (StringUtils.isNotBlank(bus.getClincClassName())) {
            criteria.andLike("clincClassName", bus.getClincClassName() + "%");
        }

        Page<BusClincClass> busList = (Page<BusClincClass>) busClincClassMapper.selectByExample(example);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 新增或者更新科室
     *
     *
     * @param bus
     * @param request
     * @return
     */
    @Override
    public ComResponse<BusClincClass> insertOrUpdateClincClass(BusClincClass bus, HttpServletRequest request) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            bus.setEditTime(new Date());
            bus.setEditBy(jhiUser.getId());
            bus.setEditName(jhiUser.getFirstName());
            if (null != bus.getId()) {
                busClincClassMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateTime(new Date());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                busClincClassMapper.insertSelective(bus);
            }
        } else {
            return ComResponse.failUnauthorized();
        }

        return ComResponse.ok(bus);
    }


    /**
     * 根据id删除科室
     *
     * @param ids
     * @return
     */
    @Override
    public ComResponse<StringBuilder> deleteClincClass(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusClincClass delRecord = new BusClincClass();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busClincClassMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

}
