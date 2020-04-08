package com.doctor.visit.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user")
public class SysUser implements Serializable {
    /**
     * 用户ID
     */
    @Id
    @Column(name = "YHID")
    private String yhid;

    /**
     * 机构ID
     */
    @Column(name = "JGID")
    private String jgid;

    /**
     * 登录名
     */
    @Column(name = "ACCOUNT")
    private String account;

    /**
     * 用户密码
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 姓名
     */
    @Column(name = "XM")
    private String xm;

    /**
     * 性别1=男，2=女，9=未说明的性别 0=未知的性别
     */
    @Column(name = "XB")
    private String xb;

    /**
     * 出生年月
     */
    @Column(name = "CSRQ")
    private Date csrq;

    /**
     * 证件类型
     */
    @Column(name = "ZJLX")
    private String zjlx;

    /**
     * 证件号码
     */
    @Column(name = "SFZHM")
    private String sfzhm;

    /**
     * 学历
     */
    @Column(name = "XL")
    private String xl;

    /**
     * 政治面貌
     */
    @Column(name = "ZZMM")
    private String zzmm;

    /**
     * 联系方式
     */
    @Column(name = "LXFS")
    private String lxfs;

    /**
     * 设备编码
     */
    @Column(name = "SBBM")
    private String sbbm;

    /**
     * 创建人
     */
    @Column(name = "CJR")
    private String cjr;

    /**
     * 创建日期
     */
    @Column(name = "CJRQ")
    private Date cjrq;

    /**
     * 创建单位
     */
    @Column(name = "CJDW")
    private String cjdw;

    /**
     * 更新人
     */
    @Column(name = "GXR")
    private String gxr;

    /**
     * 更新日期
     */
    @Column(name = "GXRQ")
    private Date gxrq;

    /**
     * 更新单位
     */
    @Column(name = "GXDW")
    private String gxdw;

    /**
     * 有效标志，0待审核，1通过审核，2驳回，3禁用
     */
    @Column(name = "YXBZ")
    private BigDecimal yxbz;

    /**
     * 民族
     */
    @Column(name = "MZ")
    private String mz;

    /**
     * 盐
     */
    @Column(name = "SALT")
    private String salt;

    /**
     * 行政区划码
     */
    @Column(name = "CODE")
    private String code;

    /**
     * IP地址
     */
    @Column(name = "IP")
    private String ip;

    /**
     * 机构名称
     */
    @Column(name = "JGMC")
    private String jgmc;

    /**
     * 部门
     */
    @Column(name = "DEPARTMENT")
    private String department;

    /**
     * 经度
     */
    private String lng;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 定位类型：1(GPS)，2(百度综合定位)
     */
    @Column(name = "location_type")
    private String locationType;

    /**
     * 职务
     */
    private String position;

    /**
     * 是否网格员（1是，0否）
     */
    @Column(name = "is_wgy")
    private String isWgy;

    /**
     * 是否已注册指挥调度账号（1是，0否）
     */
    @Column(name = "ZHDD")
    private String zhdd;

    /**
     * 详细地址
     */
    @Column(name = "XXDZ")
    private String xxdz;

    @Column(name = "IMG")
    private String img;

    private static final long serialVersionUID = 1L;

    /**
     * 获取用户ID
     *
     * @return YHID - 用户ID
     */
    public String getYhid() {
        return yhid;
    }

    /**
     * 设置用户ID
     *
     * @param yhid 用户ID
     */
    public void setYhid(String yhid) {
        this.yhid = yhid;
    }

    /**
     * 获取机构ID
     *
     * @return JGID - 机构ID
     */
    public String getJgid() {
        return jgid;
    }

    /**
     * 设置机构ID
     *
     * @param jgid 机构ID
     */
    public void setJgid(String jgid) {
        this.jgid = jgid;
    }

    /**
     * 获取登录名
     *
     * @return ACCOUNT - 登录名
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置登录名
     *
     * @param account 登录名
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取用户密码
     *
     * @return PASSWORD - 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取姓名
     *
     * @return XM - 姓名
     */
    public String getXm() {
        return xm;
    }

    /**
     * 设置姓名
     *
     * @param xm 姓名
     */
    public void setXm(String xm) {
        this.xm = xm;
    }

    /**
     * 获取性别1=男，2=女，9=未说明的性别 0=未知的性别
     *
     * @return XB - 性别1=男，2=女，9=未说明的性别 0=未知的性别
     */
    public String getXb() {
        return xb;
    }

    /**
     * 设置性别1=男，2=女，9=未说明的性别 0=未知的性别
     *
     * @param xb 性别1=男，2=女，9=未说明的性别 0=未知的性别
     */
    public void setXb(String xb) {
        this.xb = xb;
    }

    /**
     * 获取出生年月
     *
     * @return CSRQ - 出生年月
     */
    public Date getCsrq() {
        return csrq;
    }

    /**
     * 设置出生年月
     *
     * @param csrq 出生年月
     */
    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    /**
     * 获取证件类型
     *
     * @return ZJLX - 证件类型
     */
    public String getZjlx() {
        return zjlx;
    }

    /**
     * 设置证件类型
     *
     * @param zjlx 证件类型
     */
    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    /**
     * 获取证件号码
     *
     * @return SFZHM - 证件号码
     */
    public String getSfzhm() {
        return sfzhm;
    }

    /**
     * 设置证件号码
     *
     * @param sfzhm 证件号码
     */
    public void setSfzhm(String sfzhm) {
        this.sfzhm = sfzhm;
    }

    /**
     * 获取学历
     *
     * @return XL - 学历
     */
    public String getXl() {
        return xl;
    }

    /**
     * 设置学历
     *
     * @param xl 学历
     */
    public void setXl(String xl) {
        this.xl = xl;
    }

    /**
     * 获取政治面貌
     *
     * @return ZZMM - 政治面貌
     */
    public String getZzmm() {
        return zzmm;
    }

    /**
     * 设置政治面貌
     *
     * @param zzmm 政治面貌
     */
    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    /**
     * 获取联系方式
     *
     * @return LXFS - 联系方式
     */
    public String getLxfs() {
        return lxfs;
    }

    /**
     * 设置联系方式
     *
     * @param lxfs 联系方式
     */
    public void setLxfs(String lxfs) {
        this.lxfs = lxfs;
    }

    /**
     * 获取设备编码
     *
     * @return SBBM - 设备编码
     */
    public String getSbbm() {
        return sbbm;
    }

    /**
     * 设置设备编码
     *
     * @param sbbm 设备编码
     */
    public void setSbbm(String sbbm) {
        this.sbbm = sbbm;
    }

    /**
     * 获取创建人
     *
     * @return CJR - 创建人
     */
    public String getCjr() {
        return cjr;
    }

    /**
     * 设置创建人
     *
     * @param cjr 创建人
     */
    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    /**
     * 获取创建日期
     *
     * @return CJRQ - 创建日期
     */
    public Date getCjrq() {
        return cjrq;
    }

    /**
     * 设置创建日期
     *
     * @param cjrq 创建日期
     */
    public void setCjrq(Date cjrq) {
        this.cjrq = cjrq;
    }

    /**
     * 获取创建单位
     *
     * @return CJDW - 创建单位
     */
    public String getCjdw() {
        return cjdw;
    }

    /**
     * 设置创建单位
     *
     * @param cjdw 创建单位
     */
    public void setCjdw(String cjdw) {
        this.cjdw = cjdw;
    }

    /**
     * 获取更新人
     *
     * @return GXR - 更新人
     */
    public String getGxr() {
        return gxr;
    }

    /**
     * 设置更新人
     *
     * @param gxr 更新人
     */
    public void setGxr(String gxr) {
        this.gxr = gxr;
    }

    /**
     * 获取更新日期
     *
     * @return GXRQ - 更新日期
     */
    public Date getGxrq() {
        return gxrq;
    }

    /**
     * 设置更新日期
     *
     * @param gxrq 更新日期
     */
    public void setGxrq(Date gxrq) {
        this.gxrq = gxrq;
    }

    /**
     * 获取更新单位
     *
     * @return GXDW - 更新单位
     */
    public String getGxdw() {
        return gxdw;
    }

    /**
     * 设置更新单位
     *
     * @param gxdw 更新单位
     */
    public void setGxdw(String gxdw) {
        this.gxdw = gxdw;
    }

    /**
     * 获取有效标志，0待审核，1通过审核，2驳回，3禁用
     *
     * @return YXBZ - 有效标志，0待审核，1通过审核，2驳回，3禁用
     */
    public BigDecimal getYxbz() {
        return yxbz;
    }

    /**
     * 设置有效标志，0待审核，1通过审核，2驳回，3禁用
     *
     * @param yxbz 有效标志，0待审核，1通过审核，2驳回，3禁用
     */
    public void setYxbz(BigDecimal yxbz) {
        this.yxbz = yxbz;
    }

    /**
     * 获取民族
     *
     * @return MZ - 民族
     */
    public String getMz() {
        return mz;
    }

    /**
     * 设置民族
     *
     * @param mz 民族
     */
    public void setMz(String mz) {
        this.mz = mz;
    }

    /**
     * 获取盐
     *
     * @return SALT - 盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置盐
     *
     * @param salt 盐
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 获取行政区划码
     *
     * @return CODE - 行政区划码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置行政区划码
     *
     * @param code 行政区划码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取IP地址
     *
     * @return IP - IP地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置IP地址
     *
     * @param ip IP地址
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取机构名称
     *
     * @return JGMC - 机构名称
     */
    public String getJgmc() {
        return jgmc;
    }

    /**
     * 设置机构名称
     *
     * @param jgmc 机构名称
     */
    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    /**
     * 获取部门
     *
     * @return DEPARTMENT - 部门
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 设置部门
     *
     * @param department 部门
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * 获取经度
     *
     * @return lng - 经度
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
     * 获取纬度
     *
     * @return lat - 纬度
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
     * 获取定位类型：1(GPS)，2(百度综合定位)
     *
     * @return location_type - 定位类型：1(GPS)，2(百度综合定位)
     */
    public String getLocationType() {
        return locationType;
    }

    /**
     * 设置定位类型：1(GPS)，2(百度综合定位)
     *
     * @param locationType 定位类型：1(GPS)，2(百度综合定位)
     */
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    /**
     * 获取职务
     *
     * @return position - 职务
     */
    public String getPosition() {
        return position;
    }

    /**
     * 设置职务
     *
     * @param position 职务
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * 获取是否网格员（1是，0否）
     *
     * @return is_wgy - 是否网格员（1是，0否）
     */
    public String getIsWgy() {
        return isWgy;
    }

    /**
     * 设置是否网格员（1是，0否）
     *
     * @param isWgy 是否网格员（1是，0否）
     */
    public void setIsWgy(String isWgy) {
        this.isWgy = isWgy;
    }

    /**
     * 获取是否已注册指挥调度账号（1是，0否）
     *
     * @return ZHDD - 是否已注册指挥调度账号（1是，0否）
     */
    public String getZhdd() {
        return zhdd;
    }

    /**
     * 设置是否已注册指挥调度账号（1是，0否）
     *
     * @param zhdd 是否已注册指挥调度账号（1是，0否）
     */
    public void setZhdd(String zhdd) {
        this.zhdd = zhdd;
    }

    /**
     * 获取详细地址
     *
     * @return XXDZ - 详细地址
     */
    public String getXxdz() {
        return xxdz;
    }

    /**
     * 设置详细地址
     *
     * @param xxdz 详细地址
     */
    public void setXxdz(String xxdz) {
        this.xxdz = xxdz;
    }

    /**
     * @return IMG
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img
     */
    public void setImg(String img) {
        this.img = img;
    }
}