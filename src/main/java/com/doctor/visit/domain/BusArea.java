package com.doctor.visit.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Table(name = "bus_area")
public class BusArea implements Serializable {
    /**
     * 网格编码
     */
    @Id
    @Column(name = "WGBM")
    private String wgbm;

    /**
     * 上级网格编码
     */
    @Id
    @Column(name = "SJWGBM")
    private String sjwgbm;

    /**
     * 网格名称
     */
    @Column(name = "WGMC")
    private String wgmc;

    /**
     * 网格类型,省，市，区县，镇街，片区，村，网格
     */
    @Column(name = "WGLX")
    private String wglx;

    /**
     * 网格地址
     */
    @Column(name = "WGDZ")
    private String wgdz;

    /**
     * 国标
     */
    @Column(name = "GB")
    private String gb;

    /**
     * 网格级别，1，2，3，4，5，6，7
     */
    @Column(name = "WGJB")
    private Long wgjb;

    /**
     * 排序
     */
    @Column(name = "SORT")
    private Integer sort;

    /**
     * 纬度
     */
    @Column(name = "LAT")
    private String lat;

    /**
     * 经度
     */
    @Column(name = "LNG")
    private String lng;

    /**
     * 是否删除，1是0否
     */
    @Column(name = "is_del")
    private String isDel;

    private static final long serialVersionUID = 1L;

    /**
     * 获取网格编码
     *
     * @return WGBM - 网格编码
     */
    public String getWgbm() {
        return wgbm;
    }

    /**
     * 设置网格编码
     *
     * @param wgbm 网格编码
     */
    public void setWgbm(String wgbm) {
        this.wgbm = wgbm;
    }

    /**
     * 获取上级网格编码
     *
     * @return SJWGBM - 上级网格编码
     */
    public String getSjwgbm() {
        return sjwgbm;
    }

    /**
     * 设置上级网格编码
     *
     * @param sjwgbm 上级网格编码
     */
    public void setSjwgbm(String sjwgbm) {
        this.sjwgbm = sjwgbm;
    }

    /**
     * 获取网格名称
     *
     * @return WGMC - 网格名称
     */
    public String getWgmc() {
        return wgmc;
    }

    /**
     * 设置网格名称
     *
     * @param wgmc 网格名称
     */
    public void setWgmc(String wgmc) {
        this.wgmc = wgmc;
    }

    /**
     * 获取网格类型,省，市，区县，镇街，片区，村，网格
     *
     * @return WGLX - 网格类型,省，市，区县，镇街，片区，村，网格
     */
    public String getWglx() {
        return wglx;
    }

    /**
     * 设置网格类型,省，市，区县，镇街，片区，村，网格
     *
     * @param wglx 网格类型,省，市，区县，镇街，片区，村，网格
     */
    public void setWglx(String wglx) {
        this.wglx = wglx;
    }

    /**
     * 获取网格地址
     *
     * @return WGDZ - 网格地址
     */
    public String getWgdz() {
        return wgdz;
    }

    /**
     * 设置网格地址
     *
     * @param wgdz 网格地址
     */
    public void setWgdz(String wgdz) {
        this.wgdz = wgdz;
    }

    /**
     * 获取国标
     *
     * @return GB - 国标
     */
    public String getGb() {
        return gb;
    }

    /**
     * 设置国标
     *
     * @param gb 国标
     */
    public void setGb(String gb) {
        this.gb = gb;
    }

    /**
     * 获取网格级别，1，2，3，4，5，6，7
     *
     * @return WGJB - 网格级别，1，2，3，4，5，6，7
     */
    public Long getWgjb() {
        return wgjb;
    }

    /**
     * 设置网格级别，1，2，3，4，5，6，7
     *
     * @param wgjb 网格级别，1，2，3，4，5，6，7
     */
    public void setWgjb(Long wgjb) {
        this.wgjb = wgjb;
    }

    /**
     * 获取排序
     *
     * @return SORT - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取纬度
     *
     * @return LAT - 纬度
     */
    public String getLat() {
        return lat;
    }

    /**
     * 设置纬度
     *
     * @param lat 纬度
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * 获取经度
     *
     * @return LNG - 经度
     */
    public String getLng() {
        return lng;
    }

    /**
     * 设置经度
     *
     * @param lng 经度
     */
    public void setLng(String lng) {
        this.lng = lng;
    }
    /**
     * 获取是否删除，1是0否
     *
     * @return is_del - 是否删除，1是0否
     */
    public String getIsDel() {
        return isDel;
    }

    /**
     * 设置是否删除，1是0否
     *
     * @param isDel 是否删除，1是0否
     */
    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }
}
