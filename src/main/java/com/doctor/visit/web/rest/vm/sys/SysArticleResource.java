package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusArticle;
import com.doctor.visit.domain.BusArticleClass;
import com.doctor.visit.service.ArticleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取文章列表
 *
 * @author kuanwang
 * @date 2020-04-02
 */
@RestController
@RequestMapping(Constants.API_BASE_SYS + "/article")
public class SysArticleResource {

    private final ArticleService articleService;

    public SysArticleResource(ArticleService articleService) {
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
     * 新增或者修改文章分类
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusArticleClass.class)
    })
    @PostMapping("insertOrUpdateArticleClass")
    @ApiOperation(value = "新增或者修改文章分类")
    public Object insertOrUpdateArticleClass(BusArticleClass bus) {
        return articleService.insertOrUpdateArticleClass(bus);
    }

    /**
     * 根据id删除文章分类
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("deleteArticleClass")
    @ApiOperation(value = "根据id删除文章分类")
    public Object deleteArticleClass(String ids) {
        return articleService.deleteArticleClass(ids);
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
     * 新增或者修改文章
     *
     * @param bus
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusArticle.class)
    })
    @PostMapping("insertOrUpdateArticle")
    @ApiOperation(value = "新增或者修改文章")
    public Object insertOrUpdateArticle(BusArticle bus) {
        return articleService.insertOrUpdateArticle(bus);
    }

    /**
     * 根据id删除文章
     *
     * @param ids
     * @return
     */
    @ApiImplicitParams({

    })
    @PostMapping("deleteArticle")
    @ApiOperation(value = "根据id删除文章")
    public Object deleteArticle(String ids) {
        return articleService.deleteArticle(ids);
    }

}
