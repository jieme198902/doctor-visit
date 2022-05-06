package com.doctor.visit.service;

import com.doctor.visit.domain.BusArticle;
import com.doctor.visit.domain.BusArticleClass;
import com.doctor.visit.domain.BusRelationUserArticle;
import com.doctor.visit.domain.BusRelationUserArticleShare;
import com.doctor.visit.domain.dto.BusArticleDto;
import com.doctor.visit.web.rest.util.ComResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ArticleService {
    /**
     * 后台 - 查询文章分类列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse listArticleClass(BusArticleClass bus, Pageable pageable);

    /**
     * 后台 - 新增或者更新文章分类
     *
     * @param bus
     * @return
     */
    ComResponse<BusArticleClass> insertOrUpdateArticleClass(BusArticleClass bus);

    /**
     * 根据id删除文章分类
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteArticleClass(String ids);

    /**
     * 前台 - 获取文章列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusArticleDto>> listArticle(BusArticle bus, Pageable pageable, HttpServletRequest request, boolean sys) throws Exception;


    /**
     * 前台 - 获取文章详情
     *
     * @param bus
     * @return
     */
    ComResponse<BusArticleDto> oneArticle(BusArticle bus, HttpServletRequest request) throws Exception;

    /**
     * 前台 - 获取我收藏的文章列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusArticleDto>> listFavArticle(BusArticle bus, Pageable pageable, HttpServletRequest request) throws Exception;

    /**
     * 新增或者更新文章
     * 静态化文章生成url
     *
     * @param bus
     * @param request 处理封面
     * @return
     */
    ComResponse<BusArticle> insertOrUpdateArticle(BusArticle bus, HttpServletRequest request);

    /**
     * 根据id删除文章分类
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteArticle(String ids);

    /**
     * 前台 - 新增或者删除用户收藏的文章
     *
     * @param bus
     * @return
     */
    ComResponse insertOrUpdateRelationUserArticle(BusRelationUserArticle bus, HttpServletRequest request) throws Exception;

    /**
     * 前台 - 新增或者删除用户分享的文章
     *
     * @param bus
     * @return
     */
    ComResponse insertOrUpdateRelationUserArticleShare(BusRelationUserArticleShare bus, HttpServletRequest request) throws Exception;

    /**
     * 前台 - 获取我分享的文章列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    ComResponse<List<BusArticleDto>> listArticleShare(BusArticle bus, Pageable pageable, HttpServletRequest request) throws Exception;
}
