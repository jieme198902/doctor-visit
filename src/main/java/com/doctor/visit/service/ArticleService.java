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
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
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

    @Value("${custom.rootPath}")
    private String rootPath;
    @Value("${custom.requestPath}")
    private String requestPath;
    //
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
     * 后台 - 查询文章分类列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse listArticleClass(BusArticleClass bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Example example = new Example(BusArticleClass.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDel", Constants.EXIST);
        if (StringUtils.isNotBlank(bus.getName())) {
            criteria.andLike("name", bus.getName() + "%");
        }
        Page<BusArticleClass> busList = (Page<BusArticleClass>) busArticleClassMapper.selectByExample(example);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 后台 - 新增或者更新文章分类
     *
     * @param bus
     * @return
     */
    public ComResponse<BusArticleClass> insertOrUpdateArticleClass(BusArticleClass bus) {
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
                busArticleClassMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                bus.setCreateTime(new Date());
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
        if (StringUtils.isBlank(ids)) {
            return ComResponse.fail("ids参数为空。");
        }
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
     * 前台 - 获取文章列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusArticle>> listArticle(BusArticle bus, Pageable pageable, HttpServletRequest request, boolean sys) throws Exception {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Page<BusArticle> busList = null;
        if (sys) {
            Example example = new Example(BusArticle.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("isDel", Constants.EXIST);
            if (StringUtils.isNotBlank(bus.getTitle())) {
                criteria.andLike("title", bus.getTitle() + "%");
            }
            if (null != bus.getClassId()) {
                criteria.andEqualTo("classId", bus.getClassId());
            }
            busList = (Page<BusArticle>) busArticleMapper.selectByExample(example);
        } else {
            Long userId = Utils.getUserIdWithoutException(request);
            busList = (Page<BusArticle>) busArticleMapper.selectArticleListWithFav(userId);
        }
        return ComResponse.ok(busList.getResult(), busList.getTotal()).setStarDataListener(list -> {
            list.forEach(bean -> bean.setUrl(requestPath + bean.getUrl()));
            return list;
        });
    }

    /**
     * 前台 - 获取我收藏的文章列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusArticle>> listFavArticle(BusArticle bus, Pageable pageable, HttpServletRequest request) throws Exception {
        //获取用户的id
        bus.setCreateBy(Utils.getUserId(request));
        if (null == bus.getCreateBy()) {
            return ComResponse.failBadRequest();
        }
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Page<BusArticle> busList = (Page<BusArticle>) busArticleMapper.selectFavArticle(bus.getCreateBy());
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 新增或者更新文章
     * 静态化文章生成url
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
                //设置html的静态化，并且维护url Long busId, String bus, String title, String forwardFrom, String content
                bus.setUrl(Utils.writeHtml(new Utils.BusHtml(bus.getId(),"bus_article",bus.getTitle(),bus.getForwardFrom(),bus.getContent()), rootPath));
                busArticleMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateTime(new Date());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                //设置html的静态化，并且维护url Long busId, String bus, String title, String forwardFrom, String content
                bus.setUrl(Utils.writeHtml(new Utils.BusHtml(bus.getId(),"bus_article",bus.getTitle(),bus.getForwardFrom(),bus.getContent()), rootPath));
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
        if (StringUtils.isBlank(ids)) {
            return ComResponse.fail("ids为空");
        }
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
    public ComResponse insertOrUpdateRelationUserArticle(BusRelationUserArticle bus, HttpServletRequest request) throws Exception {
        //获取用户的id
        if (Utils.isBlank(bus.getArticleId())) {
            return ComResponse.fail("文章id为空");
        }
        //获取用户的id
        Long userId = Utils.getUserId(request);
        if (null == bus.getId()) {
            bus.setId(IDKeyUtil.generateId());
            bus.setUserId(userId);
            busRelationUserArticleMapper.insertSelective(bus);
        } else {
            bus.setUserId(userId);
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
    public ComResponse insertOrUpdateRelationUserArticleShare(BusRelationUserArticleShare bus, HttpServletRequest request) throws Exception {
        //获取用户的id
        if (Utils.isBlank(bus.getArticleId())) {
            return ComResponse.fail("文章id为空");
        }
        Long userId = Utils.getUserId(request);
        if (null == bus.getId()) {
            bus.setId(IDKeyUtil.generateId());
            bus.setUserId(userId);
            busRelationUserArticleShareMapper.insertSelective(bus);
        } else {
            bus.setUserId(userId);
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
    public ComResponse<List<BusArticle>> listArticleShare(BusArticle bus, Pageable pageable, HttpServletRequest request) throws Exception {
        //获取用户的id
        Long userId = Utils.getUserId(request);
        bus.setCreateBy(userId);
        if (null == bus.getCreateBy()) {
            return ComResponse.failBadRequest();
        }
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Page<BusArticle> busList = (Page<BusArticle>) busArticleMapper.selectShareArticle(bus.getCreateBy());
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

}
