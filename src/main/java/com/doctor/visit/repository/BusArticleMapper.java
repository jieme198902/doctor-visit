package com.doctor.visit.repository;

import com.doctor.visit.config.CommMapper;
import com.doctor.visit.domain.BusArticle;
import com.doctor.visit.domain.BusDoctor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusArticleMapper extends CommMapper<BusArticle> {


    /**
     * 我收藏的文章
     *
     * @param userId
     * @return
     */
    List<BusArticle> selectFavArticle(@Param("userId") Long userId);

    /**
     * 我分享的文章
     *
     * @param userId
     * @return
     */
    List<BusArticle> selectShareArticle(@Param("userId") Long userId);

    /**
     * 获取文章列表，包含收藏状态
     *
     * @param classId 文章分类
     * @param userId  用户id
     * @param title   标题
     * @return
     */
    List<BusArticle> selectArticleListWithFav(@Param("userId") Long userId, @Param("classId") Long classId, @Param("title") String title);
}
