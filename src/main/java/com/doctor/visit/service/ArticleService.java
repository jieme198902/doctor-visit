package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusArticle;
import com.doctor.visit.domain.BusArticleClass;
import com.doctor.visit.domain.BusRelationUserArticle;
import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.repository.BusArticleClassMapper;
import com.doctor.visit.repository.BusArticleMapper;
import com.doctor.visit.repository.BusRelationUserArticleMapper;
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
    private final BusArticleClassMapper busArticleClassMapper;
    private final BusArticleMapper busArticleMapper;
    private final BusRelationUserArticleMapper busRelationUserArticleMapper;

    public ArticleService(BusArticleClassMapper busArticleClassMapper, BusArticleMapper busArticleMapper, CommonService commonService, BusRelationUserArticleMapper busRelationUserArticleMapper) {
        this.busArticleClassMapper = busArticleClassMapper;
        this.busArticleMapper = busArticleMapper;
        this.commonService = commonService;
        this.busRelationUserArticleMapper = busRelationUserArticleMapper;
    }


    /**
     * 查询文章分类列表
     *
     * @param busArticleClass
     * @param pageable
     * @return
     */
    public ComResponse listArticleClass(BusArticleClass busArticleClass, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        busArticleClass.setIsDel(Constants.EXIST);
        Page<BusArticleClass> busArticleClassList = (Page<BusArticleClass>) busArticleClassMapper.select(busArticleClass);
        return ComResponse.ok(busArticleClassList.getResult(), busArticleClassList.getTotal());
    }

    /**
     * 新增或者更新文章分类
     *
     * @param busArticleClass
     * @return
     */
    public ComResponse<BusArticleClass> insertOrUpdateArticleClass(BusArticleClass busArticleClass) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            busArticleClass.setEditTime(new Date());
            busArticleClass.setEditBy(jhiUser.getId());
            busArticleClass.setEditName(jhiUser.getFirstName());
            if (null != busArticleClass.getId()) {
                busArticleClassMapper.updateByPrimaryKeySelective(busArticleClass);
            } else {
                busArticleClass.setId(IDKeyUtil.generateId());
                busArticleClassMapper.insertSelective(busArticleClass);
            }
        } else {
            return ComResponse.failUnauthorized();
        }

        return ComResponse.ok(busArticleClass);
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
     * 获取文章列表 FIXME 获取用户的收藏状态
     *
     * @param busArticle
     * @param pageable
     * @return
     */
    public ComResponse<List<BusArticle>> listArticle(BusArticle busArticle, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        busArticle.setIsDel(Constants.EXIST);
        Page<BusArticle> busArticleList = (Page<BusArticle>) busArticleMapper.select(busArticle);
        return ComResponse.ok(busArticleList.getResult(), busArticleList.getTotal());
    }

    /**
     * 新增或者更新文章
     * FIXME 静态化文章生成url
     *
     * @param busArticle
     * @return
     */
    public ComResponse<BusArticle> insertOrUpdateArticle(BusArticle busArticle) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            busArticle.setEditTime(new Date());
            busArticle.setEditBy(jhiUser.getId());
            busArticle.setEditName(jhiUser.getFirstName());
            if (null != busArticle.getId()) {
                busArticleMapper.updateByPrimaryKeySelective(busArticle);
            } else {
                busArticle.setId(IDKeyUtil.generateId());
                busArticleMapper.insertSelective(busArticle);
            }
        } else {
            return ComResponse.failUnauthorized();
        }

        return ComResponse.ok(busArticle);
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
     * 删除或者修改用户收藏的文章
     *
     * @param busRelationUserArticle
     * @return
     */
    public ComResponse insertOrUpdateRelationUserArticle(BusRelationUserArticle busRelationUserArticle) {
        if (null == busRelationUserArticle.getId()) {
            busRelationUserArticle.setId(IDKeyUtil.generateId());
            busRelationUserArticleMapper.insertSelective(busRelationUserArticle);
        } else {
            busRelationUserArticleMapper.updateByPrimaryKeySelective(busRelationUserArticle);
        }
        return ComResponse.ok();
    }

}
