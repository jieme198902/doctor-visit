package com.doctor.visit.service.impl;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.domain.dto.BusOrderGoodsTotalDto;
import com.doctor.visit.domain.dto.BusUserShoppingCartDto;
import com.doctor.visit.domain.param.UnifiedOrderParam;
import com.doctor.visit.repository.*;
import com.doctor.visit.service.OrderService;
import com.doctor.visit.service.common.CommonService;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.PageHelper;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 订单
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final CommonService commonService;
    private final BusDictMapper busDictMapper;
    private final BusGoodsMapper busGoodsMapper;
    private final BusOrderGoodsMapper busOrderGoodsMapper;
    private final BusOrderGoodsTotalMapper busOrderGoodsTotalMapper;
    private final BusUserShippingAddressMapper busUserShippingAddressMapper;

    public OrderServiceImpl(CommonService commonService, BusDictMapper busDictMapper, BusGoodsMapper busGoodsMapper, BusOrderGoodsMapper busOrderGoodsMapper, BusOrderGoodsTotalMapper busOrderGoodsTotalMapper,BusUserShippingAddressMapper busUserShippingAddressMapper) {
        this.commonService = commonService;
        this.busDictMapper = busDictMapper;
        this.busGoodsMapper = busGoodsMapper;
        this.busOrderGoodsMapper = busOrderGoodsMapper;
        this.busOrderGoodsTotalMapper = busOrderGoodsTotalMapper;
        this.busUserShippingAddressMapper = busUserShippingAddressMapper;
    }

    /**
     * 前台 - 单商品下单
     *
     * @param bus
     * @param request
     * @return
     */
    @Override
    public ComResponse<BusOrderGoodsTotalDto> insertOrder(BusOrderGoods bus, BusUserShippingAddress address, HttpServletRequest request) throws Exception {
        BusUser busUser = commonService.getBusUser(Utils.getUserId(request));
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }
        //商品id，商品规格id 为空的时候
        if (null == bus.getGoodsId() ||
            null == bus.getGoodsSpecificationId() ||
            null == bus.getNum() ||
            null == bus.getPrice() ||
            StringUtils.isBlank(bus.getGoodsSpecificationName())) {
            return ComResponse.failBadRequest();
        }
        if (null == address || null == address.getId() || StringUtils.isBlank(address.getAddressDetail())) {
            return ComResponse.fail("请填写邮寄地址");
        }

        //新增大订单
        BusOrderGoodsTotal insertOrder = new BusOrderGoodsTotal();
        insertOrder.setId(IDKeyUtil.generateId());
        //创建人
        insertOrder.setEditTime(new Date());
        insertOrder.setEditBy(busUser.getId());
        //这里放微信openid
        insertOrder.setEditName(busUser.getWechatOpenid());
        //修改人
        insertOrder.setCreateTime(new Date());
        insertOrder.setCreateBy(busUser.getId());
        //这里放微信openid
        insertOrder.setCreateName(busUser.getWechatOpenid());
        //
        insertOrder.setIsDel(Constants.EXIST);
        //订单状态：`order_state` char(1) DEFAULT NULL COMMENT '订单状态：0已提交，待支付；1已支付，待发货；2已支付，已发货；4已评价；5已取消',
        insertOrder.setOrderState("0");
        //订单号
        insertOrder.setOrderNo(Utils.orderNo());
        //总价格
        Integer totalPrice = bus.getPrice() * bus.getNum();
        insertOrder.setTotalPrice(totalPrice);
        //添加寄递地址信息
        insertOrder.setAddressId(address.getId());
        insertOrder.setAddressDetail(address.getAddressDetail());
        //获取remark放在大订单里面
        insertOrder.setRemark(bus.getRemark());
        busOrderGoodsTotalMapper.insertSelective(insertOrder);
        //新增小订单
        bus.setId(IDKeyUtil.generateId());
        bus.setOrderId(insertOrder.getId());
        bus.setTotalAmount(totalPrice);
        //创建人
        bus.setEditTime(new Date());
        bus.setEditBy(busUser.getId());
        //这里放微信openid
        bus.setEditName(busUser.getWechatOpenid());
        //修改人
        bus.setCreateTime(new Date());
        bus.setCreateBy(busUser.getId());
        //这里放微信openid
        bus.setCreateName(busUser.getWechatOpenid());
        bus.setIsDel(Constants.EXIST);
        busOrderGoodsMapper.insertSelective(bus);
        //构建返回数据
        BusOrderGoodsTotalDto result = new BusOrderGoodsTotalDto();
        BeanUtils.copyProperties(insertOrder, result);
        List<BusOrderGoods> busOrderGoods = Lists.newArrayList();
        busOrderGoods.add(bus);
        result.setBusOrderGoods(busOrderGoods);

        return ComResponse.ok(result);
    }


    /**
     * 前台 - 购物车下单
     *
     * @param userShoppingCart
     * @param request
     * @return
     */
    @Override
    public ComResponse<BusOrderGoodsTotalDto> insertOrderWithShoppingCart(String userShoppingCart, BusUserShippingAddress address, HttpServletRequest request) throws Exception {
        BusUser busUser = commonService.getBusUser(Utils.getUserId(request));
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }
        //购物车信息
        if (StringUtils.isBlank(userShoppingCart)) {
            return ComResponse.failBadRequest();
        }
        logger.debug("userShoppingCart-->{}",userShoppingCart);
        List<BusUserShoppingCartDto> userShoppingCarts = Utils.fromJson(userShoppingCart, new TypeToken<List<BusUserShoppingCartDto>>() {
        }.getType());
        if (null == userShoppingCarts || userShoppingCarts.isEmpty()) {
            return ComResponse.failBadRequest();
        }
        if (null == address || null == address.getId() || StringUtils.isBlank(address.getAddressDetail())) {
            return ComResponse.fail("请填写邮寄地址");
        }
        //新增大订单
        BusOrderGoodsTotal insertOrder = new BusOrderGoodsTotal();
        insertOrder.setId(IDKeyUtil.generateId());
        //创建人
        insertOrder.setEditTime(new Date());
        insertOrder.setEditBy(busUser.getId());
        //这里放微信openid
        insertOrder.setEditName(busUser.getWechatOpenid());
        //修改人
        insertOrder.setCreateTime(new Date());
        insertOrder.setCreateBy(busUser.getId());
        //这里放微信openid
        insertOrder.setCreateName(busUser.getWechatOpenid());
        //
        insertOrder.setIsDel(Constants.EXIST);
        //订单状态：`order_state` char(1) DEFAULT NULL COMMENT '订单状态：0已提交，待支付；1已支付，待发货；2已支付，已发货；4已评价；5已取消',
        insertOrder.setOrderState("0");
        //订单号
        insertOrder.setOrderNo(Utils.orderNo());
        //总价格
        Integer totalPrice = 0;
        //
        List<BusOrderGoods> busOrderGoods = Lists.newArrayList();
        StringBuilder remark = new StringBuilder();
        for (BusUserShoppingCartDto cart : userShoppingCarts) {
            BusGoods busGoods = busGoodsMapper.selectByPrimaryKey(cart.getGoodsId());
            if (null != busGoods) {
                //当前价格 * 购买数量
                totalPrice = busGoods.getCurrentPrice() * cart.getGoodsNum();
                BusOrderGoods bus = new BusOrderGoods();
                //新增小订单
                bus.setId(IDKeyUtil.generateId());
                bus.setOrderId(insertOrder.getId());
                bus.setTotalAmount(totalPrice);
                //创建人
                bus.setEditTime(new Date());
                bus.setEditBy(busUser.getId());
                //这里放微信openid
                bus.setEditName(busUser.getWechatOpenid());
                //修改人
                bus.setCreateTime(new Date());
                bus.setCreateBy(busUser.getId());
                //这里放微信openid
                bus.setCreateName(busUser.getWechatOpenid());
                bus.setIsDel(Constants.EXIST);
                bus.setRemark(cart.getRemark());
                bus.setGoodsId(cart.getGoodsId());
                bus.setGoodsSpecificationId(cart.getGoodsSpecification());
                bus.setGoodsSpecificationName(cart.getSpecificationName());
                bus.setMarketPrice(busGoods.getOriginalPrice());
                bus.setPrice(busGoods.getCurrentPrice());
                bus.setNum(cart.getGoodsNum());

                //总商品的描述
                remark.append(bus.getRemark());
                remark.append(";");
                busOrderGoodsMapper.insertSelective(bus);
                busOrderGoods.add(bus);
            } else {
                throw new RuntimeException("商品没找到");
            }
        }
        insertOrder.setTotalPrice(totalPrice);
        //设置备注
        insertOrder.setRemark(remark.toString());
        //添加寄递地址信息
        insertOrder.setAddressDetail(address.getAddressDetail());
        insertOrder.setAddressId(address.getId());
        busOrderGoodsTotalMapper.insertSelective(insertOrder);
        //构建返回数据
        BusOrderGoodsTotalDto result = new BusOrderGoodsTotalDto();
        BeanUtils.copyProperties(insertOrder, result);
        result.setBusOrderGoods(busOrderGoods);
        return ComResponse.ok(result);
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

        logger.info("Order.unifiedOrder.param-->{}", Utils.toJson(param));
        //订单号，产品id，支付描述
        if (StringUtils.isAnyBlank(param.getOut_trade_no(), param.getBody())) {
            return ComResponse.failBadRequest();
        }
        if (null == param.getTotal_fee() || null == param.getProduct_id()) {
            return ComResponse.failBadRequest();
        }
        //TODO 判断totalFee

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .hostnameVerifier((s, sslSession) -> true)
            .build();

        BusDict wxDict = new BusDict();
        wxDict.setDicType("WXPZ");
        Map<String, String> wxConfig = Maps.newHashMap();
        busDictMapper.select(wxDict).forEach(dict -> wxConfig.put(dict.getDicName(), dict.getDicValue()));

//      unifiedorder 统一支付请求地址 https://api.mch.weixin.qq.com/pay/unifiedorder
        BusDict unifiedOrderDict = new BusDict();
        unifiedOrderDict.setDicName("unifiedorder");
        BusDict unifiedOrder = busDictMapper.selectOne(unifiedOrderDict);
        if (null == unifiedOrder || StringUtils.isBlank(unifiedOrder.getDicValue())) {
            return ComResponse.fail("微信小程序配置【unifiedorder】有问题，请联系管理员");
        }
        //支付回调地址  https://wk.zhangfan.ink/doctorvisit/front/mine/updateOrderStateForPay
        BusDict notifyUrlDict = new BusDict();
        notifyUrlDict.setDicName("notify_url");
        BusDict notifyUrl = busDictMapper.selectOne(notifyUrlDict);
        if (null == notifyUrl || StringUtils.isBlank(notifyUrl.getDicValue())) {
            return ComResponse.fail("微信小程序配置【notify_url】有问题，请联系管理员");
        }

        //mch_id 商家id
        BusDict mchIdDict = new BusDict();
        mchIdDict.setDicName("mch_id");
        BusDict mchId = busDictMapper.selectOne(mchIdDict);
        if (null == mchId || StringUtils.isBlank(mchId.getDicValue())) {
            return ComResponse.fail("微信小程序配置【mch_id】有问题，请联系管理员");
        }

        BusDict apiKeyDict = new BusDict();
        apiKeyDict.setDicName("api_key");
        BusDict apiKey = busDictMapper.selectOne(apiKeyDict);
        if (null == apiKey || StringUtils.isBlank(apiKey.getDicValue())) {
            return ComResponse.fail("微信小程序配置【api_key】有问题，请联系管理员");
        }

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


        logger.debug("统一下单url-->{}", unifiedOrder.getDicValue());
        //设置其他参数
        param.setAppid(wxAppid);
        param.setNonce_str(WXPayUtil.generateNonceStr());
        param.setNotify_url(notifyUrl.getDicValue());
        param.setMch_id(mchId.getDicValue());
        param.setTrade_type("MWEB");//H5支付类型

        Map<String, String> paramMap = Utils.fromJson(param, new TypeToken<Map<String, String>>() {
        }.getType());
        //// 生成签名,官方默认MD5+商户秘钥+参数信息
        String sign = WXPayUtil.generateSignature(paramMap, apiKey.getDicValue());
        paramMap.put("sign", sign);
        String xmlParam = WXPayUtil.mapToXml(paramMap);
        logger.info("Order.unifiedOrder.xmlParam-->{}", xmlParam);
        RequestBody body = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), xmlParam);
        Request req = new Request.Builder()
            .url(unifiedOrder.getDicValue())
            .post(body).build();

        Response response = okHttpClient.newCall(req).execute();
        String xmlResult = response.body().string();
        logger.info("Order.unifiedOrder.result-->{}", Utils.toJson(xmlResult));
        if (StringUtils.isBlank(xmlResult)) {
            return ComResponse.fail("请求支付超时");
        }
        Map<String, String> resultMap = WXPayUtil.xmlToMap(xmlResult);
        logger.info("Order.unifiedOrder.resultMap-->{}", resultMap);

        if (Constants.SUCCESS.equalsIgnoreCase(resultMap.get("return_code")) &&
            Constants.SUCCESS.equalsIgnoreCase(resultMap.get("result_code"))) {
            return ComResponse.ok(resultMap);
        } else {
            return ComResponse.fail(resultMap.get("return_code")+":"+resultMap.get("return_msg")+resultMap.get("err_code") + ":" + resultMap.get("err_code_des"));
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
        Map<String, String> paramMap = WXPayUtil.xmlToMap(xmlStr.toString());
        BusOrderGoodsTotal orderGoodsTotal = new BusOrderGoodsTotal();
        orderGoodsTotal.setOrderNo(paramMap.get("out_trade_no"));
        List<BusOrderGoodsTotal> busOrderGoodsTotals = busOrderGoodsTotalMapper.select(orderGoodsTotal);
        if (null == busOrderGoodsTotals || busOrderGoodsTotals.isEmpty()) {
            logger.info("未找到该订单号");
            return Constants.FAIL;
        } else if (busOrderGoodsTotals.size() != 1) {
            logger.info("找到多个订单，订单异常");
            return Constants.FAIL;
        } else {
            //  `order_state` char(1) DEFAULT NULL COMMENT '订单状态：0已提交，待支付；1已支付，待发货；2已支付，已发货；4已评价；5已取消',
            BusOrderGoodsTotal updateOrder = busOrderGoodsTotals.get(0);
            if ("0".equals(updateOrder.getOrderState())) {
                updateOrder.setOrderState("1");
                updateOrder.setPayTime(new Date());
                busOrderGoodsTotalMapper.updateByPrimaryKeySelective(updateOrder);
                logger.info("已支付:正常支付");
                return Constants.SUCCESS;
            }
            logger.error("已支付:" + updateOrder.getOrderState());
            return Constants.SUCCESS;
        }
    }

    /**
     * 获取用户的商品订单列表
     *
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ComResponse<List<BusOrderGoodsTotalDto>> listOrder(BusOrderGoodsTotal bus, Pageable pageable, HttpServletRequest request) throws Exception {
        BusUser busUser = commonService.getBusUser(Utils.getUserId(request));
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }
        bus.setIsDel(Constants.EXIST);
        //获取用户的订单列表
        bus.setCreateBy(busUser.getId());
        //排序
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(),"create_time desc");
        List<BusOrderGoodsTotal> orderGoodsTotals = busOrderGoodsTotalMapper.select(bus);
        List<BusOrderGoodsTotalDto> result = Lists.newArrayList();
        for (BusOrderGoodsTotal it : orderGoodsTotals) {
            BusOrderGoods perOrderGoods = new BusOrderGoods();
            perOrderGoods.setOrderId(it.getId());
            perOrderGoods.setIsDel(Constants.EXIST);
            List<BusOrderGoods> busOrderGoods = busOrderGoodsMapper.select(perOrderGoods);
            BusOrderGoodsTotalDto dto = new BusOrderGoodsTotalDto();
            BeanUtils.copyProperties(it, dto);
            dto.setBusOrderGoods(busOrderGoods);
            //添加订单寄递地址信息
            BusUserShippingAddress address = busUserShippingAddressMapper.selectByPrimaryKey(it.getAddressId());
            dto.setShippingAddress(address);
            result.add(dto);
        }
        return ComResponse.ok(result);
    }


    /**
     * 确认收货
     *
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Object updateOrderReceiving(BusOrderGoodsTotal bus, HttpServletRequest request) throws Exception {
        BusUser busUser = commonService.getBusUser(Utils.getUserIdWithoutException(request));
        if (null == busUser || null == bus || null == bus.getId()) {
            return ComResponse.failUnauthorized();
        }
        BusOrderGoodsTotal updateOrder = busOrderGoodsTotalMapper.selectByPrimaryKey(bus.getId());
        if (null == updateOrder) {
            return ComResponse.fail("未找到订单信息");
        }
        //  `order_state` char(1) DEFAULT NULL COMMENT '订单状态：0已提交，待支付；1已支付，待发货；2已支付，已发货；3已收货；4已评价；5已取消',
        if ("2".equals(updateOrder.getOrderState())) {
            updateOrder.setOrderState("3");
            //收货时间
            updateOrder.setReceivingTime(new Date());
            busOrderGoodsTotalMapper.updateByPrimaryKeySelective(updateOrder);
            return ComResponse.ok(updateOrder);
        }
        return ComResponse.fail("暂不能操作收货，或者已收货，请稍后重试。");
    }

    /**
     * 取消订单
     *
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Object updateOrderCancel(BusOrderGoodsTotal bus, HttpServletRequest request) throws Exception {
        BusUser busUser = commonService.getBusUser(Utils.getUserIdWithoutException(request));
        if (null == busUser || null == bus || null == bus.getId()) {
            return ComResponse.failUnauthorized();
        }
        BusOrderGoodsTotal updateOrder = busOrderGoodsTotalMapper.selectByPrimaryKey(bus.getId());
        if (null == updateOrder) {
            return ComResponse.fail("未找到订单信息");
        }
        //  `order_state` char(1) DEFAULT NULL COMMENT '订单状态：0已提交，待支付；1已支付，待发货；2已支付，已发货；3已收货；4已评价；5已取消',
        if ("0".equals(updateOrder.getOrderState()) || "1".equals(updateOrder.getOrderState())) {
            updateOrder.setOrderState("5");
            //取消时间
            updateOrder.setCancelTime(new Date());
            busOrderGoodsTotalMapper.updateByPrimaryKeySelective(updateOrder);
            return ComResponse.ok(updateOrder);
        }
        return ComResponse.fail("订单已发货，请联系管理官退货");

    }

    /**
     * 删除订单
     *
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Object updateOrderDelete(BusOrderGoodsTotal bus, HttpServletRequest request) throws Exception {
        BusUser busUser = commonService.getBusUser(Utils.getUserIdWithoutException(request));
        if (null == busUser || null == bus || null == bus.getId()) {
            return ComResponse.failUnauthorized();
        }
        BusOrderGoodsTotal updateOrder = busOrderGoodsTotalMapper.selectByPrimaryKey(bus.getId());
        if (null == updateOrder) {
            return ComResponse.fail("未找到订单信息");
        }
        //  `order_state` char(1) DEFAULT NULL COMMENT '订单状态：0已提交，待支付；1已支付，待发货；2已支付，已发货；3已收货；4已评价；5已取消',
        updateOrder.setIsDel(Constants.DELETE);
        //删除时间
        updateOrder.setDelTime(new Date());
        busOrderGoodsTotalMapper.updateByPrimaryKeySelective(updateOrder);
        return ComResponse.ok("订单已删除");
    }
}
