package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusGoods;
import com.doctor.visit.domain.BusGoodsClass;
import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.repository.BusGoodsClassMapper;
import com.doctor.visit.repository.BusGoodsMapper;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 商品服务
 */
@Service
public class GoodsService {

    @Value("${custom.rootPath}")
    private String rootPath;
    @Value("${custom.requestPath}")
    private String requestPath;

    private final CommonService commonService;
    private final BusGoodsMapper busGoodsMapper;
    private final BusGoodsClassMapper busGoodsClassMapper;

    public GoodsService(CommonService commonService, BusGoodsClassMapper busGoodsClassMapper, BusGoodsMapper busGoodsMapper) {
        this.commonService = commonService;
        this.busGoodsClassMapper = busGoodsClassMapper;
        this.busGoodsMapper = busGoodsMapper;
    }

    /**
     * 后台 - 查询商品分类列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse listGoodsClass(BusGoodsClass bus, Pageable pageable) {
        if (null != pageable) {
            PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        }
        Example example = new Example(BusGoodsClass.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDel", Constants.EXIST);
        if (StringUtils.isNotBlank(bus.getName())) {
            criteria.andLike("name", bus.getName() + "%");
        }
        if (null != pageable) {
            Page<BusGoodsClass> busList = (Page<BusGoodsClass>) busGoodsClassMapper.selectByExample(example);
            return ComResponse.ok(busList.getResult(), busList.getTotal());
        } else {
            List<BusGoodsClass> busList = busGoodsClassMapper.selectByExample(example);
            return ComResponse.ok(busList);
        }
    }

    /**
     * 后台 - 新增或者更新商品分类
     *
     * @param bus
     * @return
     */
    public ComResponse<BusGoodsClass> insertOrUpdateGoodsClass(BusGoodsClass bus) {
        if (StringUtils.isBlank(bus.getName())) {
            return ComResponse.failBadRequest();
        }
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
                busGoodsClassMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                bus.setCreateTime(new Date());
                busGoodsClassMapper.insertSelective(bus);
            }
        } else {
            return ComResponse.failUnauthorized();
        }

        return ComResponse.ok(bus);
    }

    /**
     * 根据id删除商品分类
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteGoodsClass(String ids) {
        if (StringUtils.isBlank(ids)) {
            return ComResponse.fail("ids参数为空。");
        }
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusGoodsClass delRecord = new BusGoodsClass();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busGoodsClassMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

    /**
     * 后台 - 查询商品列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse listGoods(BusGoods bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Example example = new Example(BusGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDel", Constants.EXIST);
        if (StringUtils.isNotBlank(bus.getName())) {
            criteria.andLike("name", bus.getName() + "%");
        }
        if(null!=bus.getClassId()){
            criteria.andEqualTo("classId",bus.getClassId());
        }
        Page<BusGoods> busList = (Page<BusGoods>) busGoodsMapper.selectByExample(example);
        return ComResponse.ok(busList.getResult(), busList.getTotal()).setStarDataListener(list -> {
            list.forEach(bean -> bean.setUrl(requestPath + bean.getUrl()));
            return list;
        });
    }

    /**
     * 新增或者更新商品
     * 静态化商品生成url
     *
     * @param bus
     * @return
     */
    public ComResponse<BusGoods> insertOrUpdateGoods(BusGoods bus) {
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
                //设置html的静态化，并且维护url Long busId, String bus, String title, String forwardFrom, String content
                bus.setUrl(Utils.writeHtml(new Utils.BusHtml(bus.getId(), "bus_goods", bus.getName(), bus.getName(), bus.getContent()), rootPath));
                busGoodsMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateTime(new Date());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                //设置html的静态化，并且维护url
                bus.setUrl(Utils.writeHtml(new Utils.BusHtml(bus.getId(), "bus_goods", bus.getName(), bus.getName(), bus.getContent()), rootPath));
                busGoodsMapper.insertSelective(bus);
            }
        } else {
            return ComResponse.failUnauthorized();
        }

        return ComResponse.ok(bus);
    }


    /**
     * 根据id删除商品
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteGoods(String ids) {
        if (StringUtils.isBlank(ids)) {
            return ComResponse.fail("ids为空");
        }
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusGoods delRecord = new BusGoods();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busGoodsMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }
}
