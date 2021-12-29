package com.doctor.visit.service.impl;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.domain.dto.BusOrderInquiryDto;
import com.doctor.visit.domain.param.UnifiedOrderParam;
import com.doctor.visit.repository.*;
import com.doctor.visit.service.DictService;
import com.doctor.visit.service.OrderInquiryService;
import com.doctor.visit.service.common.CommonService;
import com.doctor.visit.service.common.MyWXPayConfig;
import com.doctor.visit.service.common.UploadService;
import com.doctor.visit.web.rest.util.BeanConversionUtil;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.doctor.visit.config.Constants.SUCCESS;

/**
 * 问诊订单
 */
@Service
public class OrderInquiryServiceImpl implements com.doctor.visit.service.OrderInquiryService {

    private static final Logger logger = LoggerFactory.getLogger(OrderInquiryService.class);


    //已修改
    @Value("${custom.requestPath}")
    private String requestPath;

    private final CommonService commonService;
    private final UploadService uploadService;
    private final DictService dictService;
    //
    private final BusDictMapper busDictMapper;
    private final BusFileMapper busFileMapper;
    private final BusDoctorMapper busDoctorMapper;
    private final BusPatientMapper busPatientMapper;
    private final BusOrderInquiryMapper busOrderInquiryMapper;
    private final BusGoodsInquiryMapper busGoodsInquiryMapper;
    private final BusOrderChangeRecordMapper busOrderChangeRecordMapper;

    public OrderInquiryServiceImpl(CommonService commonService, UploadService uploadService,DictService dictService, BusDictMapper busDictMapper,BusFileMapper busFileMapper, BusDoctorMapper busDoctorMapper, BusPatientMapper busPatientMapper, BusOrderInquiryMapper busOrderInquiryMapper, BusGoodsInquiryMapper busGoodsInquiryMapper, BusOrderChangeRecordMapper busOrderChangeRecordMapper) {
        this.commonService = commonService;
        this.uploadService = uploadService;
        this.dictService = dictService;
        this.busDictMapper = busDictMapper;
        this.busFileMapper = busFileMapper;
        this.busDoctorMapper = busDoctorMapper;
        this.busPatientMapper = busPatientMapper;
        this.busOrderInquiryMapper = busOrderInquiryMapper;
        this.busGoodsInquiryMapper = busGoodsInquiryMapper;
        this.busOrderChangeRecordMapper = busOrderChangeRecordMapper;
    }

    /**
     * 获取问诊订单列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @Override
    public ComResponse<List<BusOrderInquiryDto>> listOrderInquiry(BusOrderInquiry bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Example example = new Example(BusOrderInquiry.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDel", Constants.EXIST);
        if (StringUtils.isNotBlank(bus.getOrderNo())) {
            criteria.andEqualTo("orderNo", bus.getOrderNo());
        }
        if (null != bus.getCreateBy()) {
            criteria.andEqualTo("createBy", bus.getCreateBy());
        }
        Page<BusOrderInquiry> busList = (Page<BusOrderInquiry>) busOrderInquiryMapper.selectByExample(example);
        List<BusOrderInquiryDto> busDtoList = Lists.newArrayList();
        ComResponse<List<BusDict>> requestPathCom = dictService.listDistByType("requestPath");
        if(requestPathCom.isSuccess()){
            requestPath = requestPathCom.getData().get(0).getDicValue();
        }
        busList.forEach(busOrderInquiry -> busDtoList.add(BeanConversionUtil.beanToDto(busOrderInquiry, requestPath, busFileMapper, busPatientMapper, busGoodsInquiryMapper, busDoctorMapper)));
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
    @Override
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
    @Override
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
        ComResponse<List<BusDict>> requestPathCom = dictService.listDistByType("requestPath");
        if(requestPathCom.isSuccess()){
            requestPath = requestPathCom.getData().get(0).getDicValue();
        }
        BusOrderInquiryDto busOrderInquiryDto = BeanConversionUtil.beanToDto(bus, requestPath, busFileMapper, busPatientMapper, busGoodsInquiryMapper, busDoctorMapper);
        return ComResponse.ok(busOrderInquiryDto);
    }


    /**
     * 根据id删除问诊订单
     *
     * @param ids
     * @return
     */
    @Override
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


    /**
     * 统一下单:商户在小程序中先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易后调起支付
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Object unifiedOrder(UnifiedOrderParam param, HttpServletRequest request) throws Exception {

        logger.info("OrderInquiry.unifiedOrder.param-->{}", Utils.toJson(param));
        //订单号，产品id，支付描述
        if (StringUtils.isAnyBlank(param.getOut_trade_no(),  param.getBody())) {
            return ComResponse.failBadRequest();
        }
        if (null == param.getTotal_fee() || null == param.getProduct_id()) {
            return ComResponse.failBadRequest();
        }
        //openid
        if(StringUtils.isBlank(param.getOpenid())){
            return ComResponse.failBadRequest();
        }

        //判断totalFee
        BusGoodsInquiry busGoodsInquiry = busGoodsInquiryMapper.selectByPrimaryKey(param.getProduct_id());
        if(null==busGoodsInquiry){
            return ComResponse.fail("该商品不存在，请联系管理员！");
        }else{
            if(!busGoodsInquiry.getCurrentPrice().equals(param.getTotal_fee())){
                return ComResponse.fail("该商品价格已过期，请获取最新商品信息下单！");
            }
        }

        BusDict wxDict = new BusDict();
        wxDict.setDicType("WXPZ");
        Map<String, String> wxConfig = Maps.newHashMap();
        busDictMapper.select(wxDict).forEach(dict -> wxConfig.put(dict.getDicName(), dict.getDicValue()));
        //微信appid
        String wxAppid = wxConfig.get("wx_appid");
        if (StringUtils.isBlank(wxAppid)) {
            return ComResponse.fail("微信小程序配置【wx_appid】有问题，请联系管理员");
        }
        //微信secret
        String wxSecret = wxConfig.get("wx_secret");
        if (StringUtils.isBlank(wxSecret)) {
            return ComResponse.fail("微信小程序配置【wx_secret】有问题，请联系管理员");
        }

        //支付回调地址  https://www.syjk.vip/doctorvisit/front/order/inquiry/updateOrderStateForPay
        BusDict notifyUrlDict = new BusDict();
        notifyUrlDict.setDicName("notify_url_wz");
        BusDict notifyUrl = busDictMapper.selectOne(notifyUrlDict);
        if (null == notifyUrl || StringUtils.isBlank(notifyUrl.getDicValue())) {
            return ComResponse.fail("微信小程序配置【notify_url_wz】有问题，请联系管理员");
        }

        //mch_id 商家id
        BusDict mchIdDict = new BusDict();
        mchIdDict.setDicName("mch_id");
        BusDict mchId = busDictMapper.selectOne(mchIdDict);
        if (null == mchId || StringUtils.isBlank(mchId.getDicValue())) {
            return ComResponse.fail("微信小程序配置【mch_id】有问题，请联系管理员");
        }
        // mch_key 商户秘钥
        BusDict mchKeyDict = new BusDict();
        mchKeyDict.setDicName("mch_key");
        BusDict mchKey = busDictMapper.selectOne(mchKeyDict);
        if (null == mchKey || StringUtils.isBlank(mchKey.getDicValue())) {
            return ComResponse.fail("微信小程序配置【mch_key】有问题，请联系管理员");
        }
        //创建支付配置文件
        WXPayConfig config = new MyWXPayConfig(wxAppid,mchKey.getDicValue(),mchId.getDicValue());
        WXPay wxPay = new WXPay(config);

        //设置其他参数
        param.setAppid(wxAppid);
        param.setNonce_str(WXPayUtil.generateNonceStr());
        param.setNotify_url(notifyUrl.getDicValue());
        param.setMch_id(mchId.getDicValue());
        param.setTrade_type("JSAPI");//此处指定支付类型 H5支付类型 JSAPI小程序支付  NATIVE扫码支付
        param.setFee_type("CNY");
        param.setSpbill_create_ip(Utils.getIpAddress(request));

        Map<String, String> paramMap = Utils.fromJson(param, new TypeToken<Map<String, String>>() {
        }.getType());
        //// 生成签名,官方默认MD5+商户秘钥+参数信息
        String sign = WXPayUtil.generateSignature(paramMap, config.getKey());
        paramMap.put("sign", sign);
        //参数组装
        logger.info("OrderInquiry.unifiedOrder.param-->{}", paramMap);
        //发起请求
        Map<String,String> resp = wxPay.unifiedOrder(paramMap);
        //返回数据
        logger.info("OrderInquiry.unifiedOrder.resp-->{}", resp);

        if (SUCCESS.equals(resp.get("return_code"))) {
            //再次签名
            /** 重要的事情说三遍  小程序支付 所有的字段必须大写 驼峰模式 严格按照小程序支付文档
             * https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=3#
             */
            Map<String, String> reData = new HashMap<>();
            reData.put("appId", config.getAppID());
            reData.put("nonceStr", resp.get("nonce_str"));
            String newPackage = "prepay_id=" + resp.get("prepay_id");
            reData.put("package", newPackage);
            reData.put("signType","MD5");
            reData.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
            String newSign = WXPayUtil.generateSignature(reData, config.getKey());
            resp.put("paySign",newSign);
            resp.put("timeStamp", reData.get("timeStamp"));
            return ComResponse.ok(resp);
        }else{
            return ComResponse.fail(resp.get("return_msg"));
        }
    }

    /**
     * 处理支付信息
     * 更新订单，支付，回调，判断金额是否正确
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Object updateOrderStateForPay(HttpServletRequest request) throws Exception {
        //接受参数
        StringBuffer xmlStr = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            String line = null;
            while ((line = reader.readLine()) != null) {
                xmlStr.append(line);
            }
        } catch (Exception e) {
            logger.error("支付回调异常-->{}", e.getMessage());
        } finally {
            if (null != reader) {
                reader.close();
            }
        }
        //
        /**
         * CREATE TABLE `bus_order_inquiry` (
         *   `id` bigint(20) NOT NULL,
         *   `order_no` varchar(24) DEFAULT NULL COMMENT '问诊方式：0电话，1图文，2视频咨询',
         *   `doctor_id` bigint(20) DEFAULT NULL COMMENT '医生id',
         *   `patient_id` bigint(20) DEFAULT NULL COMMENT '患者id',
         *   `ask_type` char(1) DEFAULT NULL COMMENT '问诊方式：0电话，1图文，2视频咨询',
         *   `price` int(10) DEFAULT NULL COMMENT '价格',
         *   `up_limit` int(11) DEFAULT NULL COMMENT '上限次数',
         *   `time_limit` int(11) DEFAULT NULL COMMENT '有效时间-分钟',
         *   `order_state` char(1) DEFAULT NULL COMMENT '订单状态：0已提交，待支付；1已支付，待接诊；2已支付，已接诊；4已评价；5已取消',
         *   `condition` varchar(500) NOT NULL COMMENT '病情描述',
         *   `see_doctor_yet` char(1) NOT NULL COMMENT '是否去医院就诊过：1是；0否',
         *   `sick_time` char(1) NOT NULL COMMENT '本次患病时长：0一周内；1一月内；2半年内；3大于半年',
         *   `remark` varchar(255) NOT NULL COMMENT '下单备注',
         *   `create_by` bigint(255) NOT NULL COMMENT '创建者id',
         *   `create_name` varchar(255) DEFAULT NULL COMMENT '创建者姓名',
         *   `create_time` datetime NOT NULL COMMENT '创建时间',
         *   `edit_by` bigint(255) NOT NULL COMMENT '修改人',
         *   `edit_name` varchar(255) DEFAULT NULL COMMENT '修改人姓名',
         *   `edit_time` datetime NOT NULL COMMENT '修改时间',
         *   `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
         *   `is_del` char(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1是0否',
         *   PRIMARY KEY (`id`)
         * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='问诊的订单';
         */
        Map<String, String> paramMap = WXPayUtil.xmlToMap(xmlStr.toString());
        BusOrderInquiry orderInquiry = new BusOrderInquiry();
        orderInquiry.setOrderNo(paramMap.get("out_trade_no"));
        List<BusOrderInquiry> busOrderInquiries = busOrderInquiryMapper.select(orderInquiry);
        if (null == busOrderInquiries || busOrderInquiries.isEmpty()) {
            logger.info("未找到该订单号");
            return Constants.FAIL;
        } else if (busOrderInquiries.size() != 1) {
            logger.info("找到多个订单，订单异常");
            return Constants.FAIL;
        } else {
            //  `order_state` char(1) DEFAULT NULL COMMENT '订单状态：0已提交，待支付；1已支付，待接诊；2已支付，已接诊；4已评价；5已取消',
            BusOrderInquiry updateOrder = busOrderInquiries.get(0);
            if ("0".equals(updateOrder.getOrderState())) {
                updateOrder.setOrderState("1");
                updateOrder.setPayTime(new Date());
                busOrderInquiryMapper.updateByPrimaryKeySelective(updateOrder);
                logger.info("已支付:正常支付");
                return Constants.SUCCESS;
            }
            logger.error("已支付:" + updateOrder.getOrderState());
            return Constants.SUCCESS;
        }
    }
}
