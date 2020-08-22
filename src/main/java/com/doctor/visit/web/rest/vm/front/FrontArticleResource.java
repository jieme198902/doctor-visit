package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusArticle;
import com.doctor.visit.domain.BusArticleClass;
import com.doctor.visit.domain.BusRelationUserArticle;
import com.doctor.visit.domain.BusRelationUserArticleShare;
import com.doctor.visit.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信文章的接口
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@Api("微信文章的接口")
@RestController
@RequestMapping(Constants.API_BASE_FRONT + "/article")
public class FrontArticleResource {
    private final ArticleService articleService;

    public FrontArticleResource(ArticleService articleService) {
        this.articleService = articleService;
    }


    /**
     * 查询文章分类列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusArticleClass.class)
    })
    @PostMapping("listArticleClass")
    @ApiOperation(value = "查询文章分类列表")
    public Object listArticleClass(BusArticleClass bus, Pageable pageable) {
        return articleService.listArticleClass(bus, pageable);
    }

    /**
     * 获取文章列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusArticle.class),
        @ApiImplicitParam(name = "classId",value = "文章类别id"),
        @ApiImplicitParam(name = "title",value = "标题"),
    })
    @PostMapping("listArticle")
    @ApiOperation(value = "文章列表")
    public Object listArticle(BusArticle bus, Pageable pageable,HttpServletRequest request) throws Exception {
        return articleService.listArticle(bus, pageable,request,false);
    }

    /**
     * 获取收藏的文章列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusArticle.class),
        @ApiImplicitParam(name = "token",value = "header中的token"),
    })
    @PostMapping("listFavArticle")
    @ApiOperation(value = "获取收藏的文章列表")
    public Object listFavArticle(BusArticle bus, Pageable pageable, HttpServletRequest request) throws Exception {
        return articleService.listFavArticle(bus, pageable, request);
    }

    /**
     * 收藏文章
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusRelationUserArticle.class),
        @ApiImplicitParam(name = "token",value = "header中的token"),
        @ApiImplicitParam(name = "articleId",value = "文章id"),
    })
    @PostMapping("favArticle")
    @ApiOperation(value = "收藏文章")
    public Object favArticle(BusRelationUserArticle bus, HttpServletRequest request) throws Exception {
        bus.setIsDel(Constants.EXIST);
        return articleService.insertOrUpdateRelationUserArticle(bus, request);
    }

    /**
     * 取消收藏文章
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusRelationUserArticle.class),
        @ApiImplicitParam(name = "token",value = "header中的token"),
        @ApiImplicitParam(name = "articleId",value = "文章id"),
    })
    @PostMapping("cancelFavArticle")
    @ApiOperation(value = "取消收藏文章")
    public Object cancelFavArticle(BusRelationUserArticle bus, HttpServletRequest request) throws Exception {
        bus.setIsDel(Constants.DELETE);
        return articleService.insertOrUpdateRelationUserArticle(bus, request);
    }

    /**
     * 分享文章
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusRelationUserArticleShare.class),
        @ApiImplicitParam(name = "token",value = "header中的token"),
        @ApiImplicitParam(name = "articleId",value = "文章id"),
    })
    @PostMapping("shareArticle")
    @ApiOperation(value = "分享文章")
    public Object shareArticle(BusRelationUserArticleShare bus, HttpServletRequest request) throws Exception {
        bus.setIsDel(Constants.EXIST);
        return articleService.insertOrUpdateRelationUserArticleShare(bus, request);
    }

    /**
     * 获取我分享的文章列表
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusArticle.class),
        @ApiImplicitParam(name = "token",value = "header中的token"),
        @ApiImplicitParam(name = "articleId",value = "文章id"),
    })
    @PostMapping("listArticleShare")
    @ApiOperation(value = "获取我分享的文章列表")
    public Object listArticleShare(BusArticle bus, Pageable pageable, HttpServletRequest request) throws Exception {
        bus.setIsDel(Constants.DELETE);
        return articleService.listArticleShare(bus, pageable, request);
    }

}
