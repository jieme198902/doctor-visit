package com.doctor.visit.service;

import com.doctor.visit.domain.BusArticle;
import com.doctor.visit.domain.BusArticleClass;
import com.doctor.visit.repository.BusArticleClassMapper;
import com.doctor.visit.repository.BusArticleMapper;
import com.doctor.visit.web.rest.util.ComResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章的信息
 *
 * @author kuanwang
 * @date 2020-06-29
 */
@Service
public class ArticleService {

    private final BusArticleClassMapper busArticleClassMapper;
    private final BusArticleMapper busArticleMapper;


    public ArticleService(BusArticleClassMapper busArticleClassMapper, BusArticleMapper busArticleMapper) {
        this.busArticleClassMapper = busArticleClassMapper;
        this.busArticleMapper = busArticleMapper;
    }


    /**
     * 新增或者更新文章分类
     *
     * @param busArticleClass
     * @return
     */
    public ComResponse insertOrUpdateArticleClass(BusArticleClass busArticleClass) {
        if (null != busArticleClass.getId()) {

        } else {

        }

        return ComResponse.OK(busArticleClass);
    }


    /**
     * 获取文章列表
     *
     * @param busArticle
     * @param pageable
     * @return
     */
    public ComResponse<List<BusArticle>> listArticle(BusArticle busArticle, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Page<BusArticle> busArticleList = (Page<BusArticle>) busArticleMapper.select(busArticle);
        return ComResponse.OK(busArticleList.getResult(), busArticleList.getTotal());
    }
}
