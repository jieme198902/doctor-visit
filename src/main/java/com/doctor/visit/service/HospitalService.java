package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.repository.BusHospitalMapper;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 医院的业务层
 *
 * @author kuanwang
 * @date 2020-06-29
 */
@Service
public class HospitalService {
    private final CommonService commonService;
    //
    private final BusHospitalMapper busHospitalMapper;

    public HospitalService(CommonService commonService, BusHospitalMapper busHospitalMapper) {
        this.commonService = commonService;
        this.busHospitalMapper = busHospitalMapper;
    }

    /**
     * 前台 - 获取医院列表
     *
     * @param busHospital
     * @param pageable
     * @return
     */
    public ComResponse<List<BusHospital>> listHospital(BusHospital busHospital, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        busHospital.setIsDel(Constants.EXIST);
        Example example = new Example(BusHospital.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(busHospital.getName())) {
            criteria.andLike("name", busHospital.getName() + "%");
        }

        Page<BusHospital> busArticleList = (Page<BusHospital>) busHospitalMapper.selectByExample(example);
        return ComResponse.ok(busArticleList.getResult(), busArticleList.getTotal());
    }

    /**
     * 新增或者更新医院
     * FIXME
     *
     * @param busHospital
     * @param request     这里需要处理文件
     * @return
     */
    public ComResponse<BusHospital> insertOrUpdateHospital(BusHospital busHospital, HttpServletRequest request) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            busHospital.setEditTime(new Date());
            busHospital.setEditBy(jhiUser.getId());
            busHospital.setEditName(jhiUser.getFirstName());
            if (null != busHospital.getId()) {
                busHospitalMapper.updateByPrimaryKeySelective(busHospital);
            } else {
                busHospital.setId(IDKeyUtil.generateId());
                busHospital.setCreateTime(new Date());
                busHospital.setCreateBy(jhiUser.getId());
                busHospital.setCreateName(jhiUser.getFirstName());
                busHospitalMapper.insertSelective(busHospital);
            }
        } else {
            return ComResponse.failUnauthorized();
        }

        return ComResponse.ok(busHospital);
    }


    /**
     * 根据id删除医院
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteHospital(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusHospital delRecord = new BusHospital();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busHospitalMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

}
