package com.doctor.visit.web.rest.vm.front;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusArticle;
import com.doctor.visit.domain.BusRelationUserArticle;
import com.doctor.visit.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 获取文章列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusArticle.class)
    })
    @PostMapping("getArticleList")
    @ApiOperation(value = "文章列表")
    public Object getArticleList(BusArticle bus, Pageable pageable) {
        return articleService.listArticle(bus, pageable);
    }

    /**
     * 获取收藏的文章列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusArticle.class)
    })
    @PostMapping("getFavArticleList")
    @ApiOperation(value = "获取收藏的文章列表")
    public Object getFavArticleList(BusArticle bus, Pageable pageable) {
        return articleService.listFavArticle(bus, pageable);
    }

    /**
     * 收藏文章
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusRelationUserArticle.class)
    })
    @PostMapping("favArticle")
    @ApiOperation(value = "收藏文章")
    public Object favArticle(BusRelationUserArticle bus) {
        bus.setIsDel(Constants.EXIST);
        return articleService.insertOrUpdateRelationUserArticle(bus);
    }

    /**
     * 取消收藏文章
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusRelationUserArticle.class)
    })
    @PostMapping("cancelFavArticle")
    @ApiOperation(value = "取消收藏文章")
    public Object cancelFavArticle(BusRelationUserArticle bus) {
        bus.setIsDel(Constants.DELETE);
        return articleService.insertOrUpdateRelationUserArticle(bus);
    }

}
