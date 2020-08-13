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
    /**
     * 封面图
     */
    private String coverImg;

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

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
