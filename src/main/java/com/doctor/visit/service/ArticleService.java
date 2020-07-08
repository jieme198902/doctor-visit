package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.repository.BusArticleClassMapper;
import com.doctor.visit.repository.BusArticleMapper;
import com.doctor.visit.repository.BusRelationUserArticleMapper;
import com.doctor.visit.repository.BusRelationUserArticleShareMapper;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 文章的信息
 *
 * @author kuanwang
 * @date 2020-06-29
 */
@Service
public class ArticleService {

    private final CommonService commonService;
    //
    private final BusArticleMapper busArticleMapper;
    private final BusArticleClassMapper busArticleClassMapper;
    private final BusRelationUserArticleMapper busRelationUserArticleMapper;
    private final BusRelationUserArticleShareMapper busRelationUserArticleShareMapper;

    public ArticleService(BusArticleClassMapper busArticleClassMapper, BusArticleMapper busArticleMapper, CommonService commonService, BusRelationUserArticleMapper busRelationUserArticleMapper, BusRelationUserArticleShareMapper busRelationUserArticleShareMapper) {
        this.busArticleClassMapper = busArticleClassMapper;
        this.busArticleMapper = busArticleMapper;
        this.commonService = commonService;
        this.busRelationUserArticleMapper = busRelationUserArticleMapper;
        this.busRelationUserArticleShareMapper = busRelationUserArticleShareMapper;
    }


    /**
     * 查询文章分类列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse listArticleClass(BusArticleClass bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        bus.setIsDel(Constants.EXIST);
        Page<BusArticleClass> busList = (Page<BusArticleClass>) busArticleClassMapper.select(bus);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 新增或者更新文章分类
     *
     * @param bus
     * @return
     */
    public ComResponse<BusArticleClass> insertOrUpdateArticleClass(BusArticleClass bus) {
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
                busArticleClassMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                busArticleClassMapper.insertSelective(bus);
            }
        } else {
            return ComResponse.failUnauthorized();
        }

        return ComResponse.ok(bus);
    }

    /**
     * 根据id删除文章分类
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteArticleClass(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusArticleClass delRecord = new BusArticleClass();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busArticleClassMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }


    /**
     * 前台 - 获取文章列表 FIXME 获取用户的收藏状态
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusArticle>> listArticle(BusArticle bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        bus.setIsDel(Constants.EXIST);
        Page<BusArticle> busList = (Page<BusArticle>) busArticleMapper.select(bus);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 前台 - 获取文章列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusArticle>> listFavArticle(BusArticle bus, Pageable pageable) {
        if (null == bus.getCreateBy()) {
            return ComResponse.failBadRequest();
        }
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Page<BusArticle> busList = (Page<BusArticle>) busArticleMapper.selectFavArticle(bus.getCreateBy());
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 新增或者更新文章
     * FIXME 静态化文章生成url
     *
     * @param bus
     * @return
     */
    public ComResponse<BusArticle> insertOrUpdateArticle(BusArticle bus) {
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
                busArticleMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                busArticleMapper.insertSelective(bus);
            }
        } else {
            return ComResponse.failUnauthorized();
        }

        return ComResponse.ok(bus);
    }


    /**
     * 根据id删除文章分类
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteArticle(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusArticle delRecord = new BusArticle();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busArticleMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

    /**
     * 前台 - 新增或者删除用户收藏的文章
     *
     * @param bus
     * @return
     */
    public ComResponse insertOrUpdateRelationUserArticle(BusRelationUserArticle bus) {
        if (null == bus.getId()) {
            bus.setId(IDKeyUtil.generateId());
            busRelationUserArticleMapper.insertSelective(bus);
        } else {
            busRelationUserArticleMapper.updateByPrimaryKeySelective(bus);
        }
        return ComResponse.ok();
    }

    /**
     * 前台 - 新增或者删除用户分享的文章
     *
     * @param bus
     * @return
     */
    public ComResponse insertOrUpdateRelationUserArticleShare(BusRelationUserArticleShare bus) {
        if (null == bus.getId()) {
            bus.setId(IDKeyUtil.generateId());
            busRelationUserArticleShareMapper.insertSelective(bus);
        } else {
            busRelationUserArticleShareMapper.updateByPrimaryKeySelective(bus);
        }
        return ComResponse.ok();
    }


    /**
     * 前台 - 获取我分享的文章列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusArticle>> listArticleShare(BusArticle bus, Pageable pageable) {
        if (null == bus.getCreateBy()) {
            return ComResponse.failBadRequest();
        }
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Page<BusArticle> busList = (Page<BusArticle>) busArticleMapper.selectShareArticle(bus.getCreateBy());
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

}
