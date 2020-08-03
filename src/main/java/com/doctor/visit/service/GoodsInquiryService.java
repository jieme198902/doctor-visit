package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusDoctor;
import com.doctor.visit.domain.BusGoodsInquiry;
import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.repository.BusDoctorMapper;
import com.doctor.visit.repository.BusGoodsInquiryMapper;
import com.doctor.visit.security.SecurityUtils;
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
 * 问诊的商品
 */
@Service
public class GoodsInquiryService {
    private final CommonService commonService;

    private final BusDoctorMapper busDoctorMapper;
    private final BusGoodsInquiryMapper busGoodsInquiryMapper;

    public GoodsInquiryService(CommonService commonService, BusDoctorMapper busDoctorMapper, BusGoodsInquiryMapper busGoodsInquiryMapper) {
        this.commonService = commonService;
        this.busDoctorMapper = busDoctorMapper;
        this.busGoodsInquiryMapper = busGoodsInquiryMapper;
    }

    /**
     * 获取问诊商品列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusGoodsInquiry>> listGoodsInquiry(BusGoodsInquiry bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());

        Example example = new Example(BusGoodsInquiry.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDel",Constants.EXIST);
        if(StringUtils.isNotBlank(bus.getDoctorName())){
            criteria.andEqualTo("doctorName",bus.getDoctorName());
        }

        Page<BusGoodsInquiry> busList = (Page<BusGoodsInquiry>) busGoodsInquiryMapper.selectByExample(example);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 新增或者更新问诊商品
     *
     * @param bus
     * @param request
     * @return
     */
    public ComResponse<BusGoodsInquiry> insertOrUpdateGoodsInquiry(BusGoodsInquiry bus, HttpServletRequest request) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();

        if (StringUtils.isBlank(bus.getDoctorName())) {
            return ComResponse.fail("医生名字未填写");
        }

        BusDoctor doctor = new BusDoctor();
        doctor.setIsDel(Constants.EXIST);
        doctor.setName(bus.getDoctorName());
        List<BusDoctor> doctors = busDoctorMapper.select(doctor);
        if (null == doctors || doctors.isEmpty()) {
            return ComResponse.fail("未找到该医生，请核实信息");
        }
        if (doctors.size() != 1) {
            return ComResponse.fail("找到多名医生信息，请核实信息");
        }
        //设置医生id
        bus.setDoctorId(doctors.get(0).getId());
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            bus.setEditTime(new Date());
            bus.setEditBy(jhiUser.getId());
            bus.setEditName(jhiUser.getFirstName());
            if (null != bus.getId()) {
                busGoodsInquiryMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateTime(new Date());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                busGoodsInquiryMapper.insertSelective(bus);
            }
        } else {
            return ComResponse.failUnauthorized();
        }
        return ComResponse.ok(bus);
    }


    /**
     * 根据id删除问诊商品
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteGoodsInquiry(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusGoodsInquiry delRecord = new BusGoodsInquiry();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busGoodsInquiryMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }
}
