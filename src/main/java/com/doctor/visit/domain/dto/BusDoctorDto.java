package com.doctor.visit.domain.dto;

import com.doctor.visit.domain.BusDoctor;
import com.doctor.visit.domain.BusGoodsInquiry;

import java.util.List;

/**
 *
 */
public class BusDoctorDto extends BusDoctor {
    /**
     * 医生的图片
     */
    private String img;
    /**
     * 是否被关注
     */
    private String attention;
    /**
     * 评分
     */
    private String score;
    /**
     * 问诊的商品
     */
    private List<BusGoodsInquiry> goodsInquiries;

    public List<BusGoodsInquiry> getGoodsInquiries() {
        return goodsInquiries;
    }

    public void setGoodsInquiries(List<BusGoodsInquiry> goodsInquiries) {
        this.goodsInquiries = goodsInquiries;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
