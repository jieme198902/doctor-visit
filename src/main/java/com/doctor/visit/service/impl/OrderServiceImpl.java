package com.doctor.visit.service.impl;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.domain.dto.BusOrderGoodsTotalDto;
import com.doctor.visit.repository.BusGoodsMapper;
import com.doctor.visit.repository.BusOrderGoodsMapper;
import com.doctor.visit.repository.BusOrderGoodsTotalMapper;
import com.doctor.visit.service.OrderService;
import com.doctor.visit.service.common.CommonService;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.PageHelper;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 订单
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final CommonService commonService;
    private final BusGoodsMapper busGoodsMapper;
    private final BusOrderGoodsMapper busOrderGoodsMapper;
    private final BusOrderGoodsTotalMapper busOrderGoodsTotalMapper;

    public OrderServiceImpl(CommonService commonService, BusGoodsMapper busGoodsMapper, BusOrderGoodsMapper busOrderGoodsMapper, BusOrderGoodsTotalMapper busOrderGoodsTotalMapper) {
        this.commonService = commonService;
        this.busGoodsMapper = busGoodsMapper;
        this.busOrderGoodsMapper = busOrderGoodsMapper;
        this.busOrderGoodsTotalMapper = busOrderGoodsTotalMapper;
    }

    /**
     * 前台 - 单商品下单
     *
     * @param bus
     * @param request
     * @return
     */
    @Override
    public ComResponse<BusOrderGoodsTotalDto> insertOrder(BusOrderGoods bus, HttpServletRequest request) throws Exception {
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

        //新增大订单
        BusOrderGoodsTotal insertOrder = new BusOrderGoodsTotal();
        insertOrder.setId(IDKeyUtil.generateId());
        //创建人
        insertOrder.setEditTime(new Date());
        insertOrder.setEditBy(busUser.getId());
        insertOrder.setEditName(busUser.getName());
        //修改人
        insertOrder.setCreateTime(new Date());
        insertOrder.setCreateBy(busUser.getId());
        insertOrder.setCreateName(busUser.getName());
        //
        insertOrder.setIsDel(Constants.EXIST);
        //订单状态：`order_state` char(1) DEFAULT NULL COMMENT '订单状态：0已提交，待支付；1已支付，待发货；2已支付，已发货；4已评价；5已取消',
        insertOrder.setOrderState("0");
        //订单号
        insertOrder.setOrderNo(Utils.orderNo());
        //总价格
        Integer totalPrice = bus.getPrice() * bus.getNum();
        insertOrder.setTotalPrice(totalPrice);
        busOrderGoodsTotalMapper.insertSelective(insertOrder);
        //新增小订单
        bus.setId(IDKeyUtil.generateId());
        bus.setOrderId(insertOrder.getId());
        bus.setTotalAmount(totalPrice);
        //创建人
        bus.setEditTime(new Date());
        bus.setEditBy(busUser.getId());
        bus.setEditName(busUser.getName());
        //修改人
        bus.setCreateTime(new Date());
        bus.setCreateBy(busUser.getId());
        bus.setCreateName(busUser.getName());
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
    public ComResponse<BusOrderGoodsTotalDto> insertOrderWithShoppingCart(String userShoppingCart, HttpServletRequest request) throws Exception {
        BusUser busUser = commonService.getBusUser(Utils.getUserId(request));
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }
        //购物车信息
        if (StringUtils.isBlank(userShoppingCart)) {
            return ComResponse.failBadRequest();
        }
        List<BusUserShoppingCart> userShoppingCarts = Utils.fromJson(userShoppingCart, new TypeToken<List<BusUserShoppingCart>>() {
        }.getType());
        if (null == userShoppingCarts || userShoppingCarts.isEmpty()) {
            return ComResponse.failBadRequest();
        }
        //新增大订单
        BusOrderGoodsTotal insertOrder = new BusOrderGoodsTotal();
        insertOrder.setId(IDKeyUtil.generateId());
        //创建人
        insertOrder.setEditTime(new Date());
        insertOrder.setEditBy(busUser.getId());
        insertOrder.setEditName(busUser.getName());
        //修改人
        insertOrder.setCreateTime(new Date());
        insertOrder.setCreateBy(busUser.getId());
        insertOrder.setCreateName(busUser.getName());
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

        for (BusUserShoppingCart cart : userShoppingCarts) {
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
                bus.setEditName(busUser.getName());
                //修改人
                bus.setCreateTime(new Date());
                bus.setCreateBy(busUser.getId());
                bus.setCreateName(busUser.getName());
                bus.setIsDel(Constants.EXIST);
                busOrderGoodsMapper.insertSelective(bus);
                busOrderGoods.add(bus);
            }
        }
        insertOrder.setTotalPrice(totalPrice);
        busOrderGoodsTotalMapper.insertSelective(insertOrder);
        //构建返回数据
        BusOrderGoodsTotalDto result = new BusOrderGoodsTotalDto();
        BeanUtils.copyProperties(insertOrder, result);
        result.setBusOrderGoods(busOrderGoods);
        return ComResponse.ok(result);
    }

    /**
     * TODO 处理支付信息
     * 更新订单，支付，回调
     *
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Object updateOrderStateForPay(BusOrderGoodsTotal bus, HttpServletRequest request) throws Exception {
        BusOrderGoodsTotal orderGoodsTotal = new BusOrderGoodsTotal();
        orderGoodsTotal.setOrderNo(bus.getOrderNo());
        List<BusOrderGoodsTotal> busOrderGoodsTotals = busOrderGoodsTotalMapper.select(orderGoodsTotal);
        if (null == busOrderGoodsTotals || busOrderGoodsTotals.isEmpty()) {
            return ComResponse.fail("未找到该订单号");
        } else if (busOrderGoodsTotals.size() != 1) {
            return ComResponse.fail("找到多个订单，订单异常");
        } else {
            //  `order_state` char(1) DEFAULT NULL COMMENT '订单状态：0已提交，待支付；1已支付，待发货；2已支付，已发货；4已评价；5已取消',
            BusOrderGoodsTotal updateOrder = busOrderGoodsTotals.get(0);
            if ("0".equals(updateOrder.getOrderState())) {
                updateOrder.setOrderState("1");
                updateOrder.setPayTime(new Date());
                busOrderGoodsTotalMapper.updateByPrimaryKeySelective(updateOrder);
                return ComResponse.ok("已支付");
            }
            return ComResponse.ok("已支付:" + updateOrder.getOrderState());

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
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());

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
            result.add(dto);
        }
        return ComResponse.ok(result);
    }
}
