package com.doctor.visit.domain.dto;

import com.doctor.visit.domain.BusArticle;

public class BusArticleDto extends BusArticle {
    /**
     * 关注
     */
    private String attention;
    /**
     * 收藏
     */
    private String collect;

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }
}
