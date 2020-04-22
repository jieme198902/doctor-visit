package com.doctor.visit.service;

import com.doctor.visit.domain.BusArticle;
import com.doctor.visit.repository.BusArticleMapper;
import com.doctor.visit.web.rest.util.ComResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class VisitService {

    private final BusArticleMapper busArticleMapper;


    public VisitService(BusArticleMapper busArticleMapper) {
        this.busArticleMapper = busArticleMapper;
    }

    /**
     * 获取文章列表
     *
     * @return
     */
    public ComResponse articleList(BusArticle busArticle, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Page<BusArticle> busArticleList = (Page<BusArticle>) busArticleMapper.select(busArticle);
        return ComResponse.OK(busArticleList.getResult(), busArticleList.getTotal());
    }
}
