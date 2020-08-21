package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusFile;
import com.doctor.visit.domain.BusOrderChangeRecord;
import com.doctor.visit.domain.BusOrderInquiry;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.domain.dto.BusOrderInquiryDto;
import com.doctor.visit.repository.*;
import com.doctor.visit.web.rest.util.BeanConversionUtil;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 问诊订单
 */
@Service
public class OrderInquiryService {

    @Value("${custom.requestPath}")
    private String requestPath;

    private final CommonService commonService;
    private final UploadService uploadService;

    //
    private final BusFileMapper busFileMapper;
    private final BusDoctorMapper busDoctorMapper;
    private final BusPatientMapper busPatientMapper;
    private final BusOrderInquiryMapper busOrderInquiryMapper;
    private final BusOrderChangeRecordMapper busOrderChangeRecordMapper;

    public OrderInquiryService(CommonService commonService, UploadService uploadService, BusFileMapper busFileMapper, BusDoctorMapper busDoctorMapper, BusPatientMapper busPatientMapper, BusOrderInquiryMapper busOrderInquiryMapper, BusOrderChangeRecordMapper busOrderChangeRecordMapper) {
        this.commonService = commonService;
        this.uploadService = uploadService;
        this.busFileMapper = busFileMapper;
        this.busDoctorMapper = busDoctorMapper;
        this.busPatientMapper = busPatientMapper;
        this.busOrderInquiryMapper = busOrderInquiryMapper;
        this.busOrderChangeRecordMapper = busOrderChangeRecordMapper;
    }

    /**
     * 获取问诊订单列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusOrderInquiryDto>> listOrderInquiry(BusOrderInquiry bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Example example = new Example(BusOrderInquiry.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDel", Constants.EXIST);
        if (StringUtils.isNotBlank(bus.getOrderNo())) {
            criteria.andEqualTo("orderNo", bus.getOrderNo());
        }
        Page<BusOrderInquiry> busList = (Page<BusOrderInquiry>) busOrderInquiryMapper.selectByExample(example);
        List<BusOrderInquiryDto> busDtoList = Lists.newArrayList();
        busList.forEach(busOrderInquiry -> busDtoList.add(BeanConversionUtil.beanToDto(busOrderInquiry, requestPath, busFileMapper, busPatientMapper, busDoctorMapper)));
        return ComResponse.ok(busDtoList, busList.getTotal());
    }


    /**
     * 更新问诊订单的状态
     * 订单状态：0已提交，待支付；1已支付，待接诊；2已支付，已接诊；4已评价；5已取消
     *
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    public ComResponse<BusOrderInquiry> insertOrUpdateOrderInquiryOrderState(BusOrderInquiry bus, HttpServletRequest request) throws Exception {
        if (null == bus.getId()) {
            return ComResponse.failBadRequest();
        }
        if (StringUtils.isBlank(bus.getOrderState())) {
            return ComResponse.failBadRequest();
        }
        //订单状态：0已提交，待支付；1已支付，待接诊；2已支付，已接诊；4已评价；5已取消
        BusOrderInquiry orderInquiry = busOrderInquiryMapper.selectByPrimaryKey(bus.getId());
        if (null == orderInquiry) {
            return ComResponse.fail("该订单查询不到了");
        }
        if (bus.getOrderState().equals(orderInquiry.getOrderState())) {
            return ComResponse.fail("订单状态已更改，请勿重复提交");
        }
        //订单状态不可以回退
        if (Integer.parseInt(bus.getOrderState()) < Integer.parseInt(orderInquiry.getOrderState())) {
            return ComResponse.fail("订单状态不可以回退到之前的状态");
        }

        //保存订单状态
        BusOrderChangeRecord orderChangeRecord = new BusOrderChangeRecord();
        orderChangeRecord.setId(IDKeyUtil.generateId());
        orderChangeRecord.setBus("bus_order_inquiry");
        orderChangeRecord.setBusId(bus.getId());
        orderChangeRecord.setCreateTime(new Date());
        orderChangeRecord.setOrderStatus(bus.getOrderState());
        busOrderChangeRecordMapper.insert(orderChangeRecord);
        //修改订单状态
        BusOrderInquiry updateOrder = new BusOrderInquiry();
        updateOrder.setOrderState(bus.getOrderState());
        updateOrder.setId(bus.getId());
        busOrderInquiryMapper.updateByPrimaryKeySelective(updateOrder);
        return ComResponse.ok();
    }

    /**
     * 前端 - 新增或者更新问诊订单
     *
     * @param bus
     * @param request 这里需要处理文件
     * @return
     */
    public ComResponse<BusOrderInquiryDto> insertOrUpdateOrderInquiry(BusOrderInquiry bus, HttpServletRequest request) throws Exception {
        bus.setCreateBy(Utils.getUserId(request));
        if (null == bus.getCreateBy()) {
            return ComResponse.failBadRequest();
        }
        BusUser busUser = commonService.getBusUser(bus.getCreateBy());
        if (null == busUser || null == bus.getDoctorId() || null == bus.getPatientId()) {
            return ComResponse.failUnauthorized();
        }
        bus.setEditTime(new Date());
        bus.setEditBy(busUser.getId());
        bus.setEditName(busUser.getName());
        if (null != bus.getId()) {
            busOrderInquiryMapper.updateByPrimaryKeySelective(bus);
        } else {
            bus.setId(IDKeyUtil.generateId());
            bus.setCreateTime(new Date());
            bus.setCreateBy(busUser.getId());
            bus.setCreateName(busUser.getName());
            bus.setOrderNo(Utils.orderNo());
            bus.setOrderState("0");
            busOrderInquiryMapper.insertSelective(bus);
        }
        //上传咨询订单
        BusFile busFile = new BusFile();
        busFile.setBus("bus_order_inquiry");
        busFile.setBusId(bus.getId());
        busFile.setFileType(Constants.FILE_TYPE_IMG);
        uploadService.uploadFiles(busFile, request);
        //
        BusOrderInquiryDto busOrderInquiryDto = BeanConversionUtil.beanToDto(bus, requestPath, busFileMapper, busPatientMapper, busDoctorMapper);
        return ComResponse.ok(busOrderInquiryDto);
    }


    /**
     * 根据id删除问诊订单
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteOrderInquiry(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusOrderInquiry delRecord = new BusOrderInquiry();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busOrderInquiryMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }
}
