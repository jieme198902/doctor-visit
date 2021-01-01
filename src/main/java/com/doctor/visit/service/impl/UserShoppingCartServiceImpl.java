package com.doctor.visit.service.impl;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.domain.BusUserShoppingCart;
import com.doctor.visit.domain.dto.BusArticleDto;
import com.doctor.visit.domain.dto.BusUserShoppingCartDto;
import com.doctor.visit.repository.BusGoodsMapper;
import com.doctor.visit.repository.BusGoodsSpecificationMapper;
import com.doctor.visit.repository.BusUserShoppingCartMapper;
import com.doctor.visit.service.common.CommonService;
import com.doctor.visit.web.rest.util.BeanConversionUtil;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.compress.utils.Lists;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 购物车
 */
@Service
public class UserShoppingCartServiceImpl implements com.doctor.visit.service.UserShoppingCartService {
    private final CommonService commonService;
    private final BusUserShoppingCartMapper busUserShoppingCartMapper;
    private final BusGoodsSpecificationMapper busGoodsSpecificationMapper;

    private final BusGoodsMapper busGoodsMapper;

    public UserShoppingCartServiceImpl(CommonService commonService, BusUserShoppingCartMapper busUserShoppingCartMapper, BusGoodsMapper busGoodsMapper, BusGoodsSpecificationMapper busGoodsSpecificationMapper) {
        this.commonService = commonService;
        this.busUserShoppingCartMapper = busUserShoppingCartMapper;
        this.busGoodsMapper = busGoodsMapper;
        this.busGoodsSpecificationMapper = busGoodsSpecificationMapper;
    }




    /**
     * 前台 - 获取用户购物车列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @Override
    public ComResponse<List<BusUserShoppingCartDto>> listUserShoppingCart(BusUserShoppingCart bus, Pageable pageable, HttpServletRequest request) throws Exception {
        BusUser busUser = commonService.getBusUser(Utils.getUserId(request));
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }

        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Example example = new Example(BusUserShoppingCart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDel",Constants.EXIST);
        //创建者
        if (null != busUser.getId()) {
            criteria.andEqualTo("createBy", busUser.getId());
        }
        //按照时间排序
        example.setOrderByClause("create_time desc");

        List<BusUserShoppingCartDto> busDtoList = Lists.newArrayList();

        Page<BusUserShoppingCart> busList = (Page<BusUserShoppingCart>) busUserShoppingCartMapper.selectByExample(example);
        busList.forEach(it ->busDtoList.add(BeanConversionUtil.beanToDto(it,busGoodsMapper,busGoodsSpecificationMapper)));

        return ComResponse.ok(busDtoList, busList.getTotal());

    }

    /**
     * 前台 - 新增或者更新用户购物车
     *
     * @param bus
     * @param request
     * @return
     */
    @Override
    public ComResponse<BusUserShoppingCart> insertOrUpdateUserShoppingCart(BusUserShoppingCart bus, HttpServletRequest request) throws Exception {
        BusUser busUser = commonService.getBusUser(Utils.getUserId(request));
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }
        bus.setEditTime(new Date());
        bus.setEditBy(busUser.getId());
        bus.setEditName(busUser.getName());
        if (null != bus.getId()) {
            busUserShoppingCartMapper.updateByPrimaryKeySelective(bus);
        } else {
            bus.setId(IDKeyUtil.generateId());
            bus.setCreateTime(new Date());
            bus.setCreateBy(busUser.getId());
            bus.setCreateName(busUser.getName());
            busUserShoppingCartMapper.insertSelective(bus);
        }

        return ComResponse.ok(bus);
    }


    /**
     * 前台 - 根据id删除用户购物车
     *
     * @param ids
     * @return
     */
    @Override
    public ComResponse<StringBuilder> deleteUserShoppingCart(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusUserShoppingCart delRecord = new BusUserShoppingCart();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busUserShoppingCartMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }else{
                throw new RuntimeException("未找到【"+id+"】购物车信息");
            }
        }
        return ComResponse.ok(delIds);
    }
}
