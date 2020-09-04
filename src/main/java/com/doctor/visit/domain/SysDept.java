package com.doctor.visit.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_dept")
public class SysDept implements Serializable {
    /**
     * ID
     */
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "dept_id")
    private Long deptId;

    /**
     * 上级部门
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;

    /**
     * 子部门数目
     */
    @Column(name = "sub_count")
    private Integer subCount;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    @Column(name = "dept_sort")
    private Integer deptSort;

    /**
     * 状态
     */
    private Boolean enabled;

    /**
     * 创建者id
     */
    @Column(name = "create_by")
    private Long createBy;

    /**
     * 创建者姓名
     */
    @Column(name = "create_name")
    private String createName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")  //取日期时使用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//存日期时使用
    private Date createTime;

    /**
     * 修改人
     */
    @Column(name = "edit_by")
    private Long editBy;

    /**
     * 修改人姓名
     */
    @Column(name = "edit_name")
    private String editName;

    /**
     * 修改时间
     */
    @Column(name = "edit_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")  //取日期时使用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//存日期时使用
    private Date editTime;

    /**
     * 是否删除，1是0否
     */
    @Column(name = "is_del")
    private String isDel;

    private static final long serialVersionUID = 1L;

    /**
     * 获取ID
     *
     * @return dept_id - ID
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * 设置ID
     *
     * @param deptId ID
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取上级部门
     *
     * @return pid - 上级部门
     */
    public Long getPid() {
        return pid;
    }

    /**
     * 设置上级部门
     *
     * @param pid 上级部门
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * 获取子部门数目
     *
     * @return sub_count - 子部门数目
     */
    public Integer getSubCount() {
        return subCount;
    }

    /**
     * 设置子部门数目
     *
     * @param subCount 子部门数目
     */
    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取排序
     *
     * @return dept_sort - 排序
     */
    public Integer getDeptSort() {
        return deptSort;
    }

    /**
     * 设置排序
     *
     * @param deptSort 排序
     */
    public void setDeptSort(Integer deptSort) {
        this.deptSort = deptSort;
    }

    /**
     * 获取状态
     *
     * @return enabled - 状态
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 设置状态
     *
     * @param enabled 状态
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 获取创建者id
     *
     * @return create_by - 创建者id
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建者id
     *
     * @param createBy 创建者id
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建者姓名
     *
     * @return create_name - 创建者姓名
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * 设置创建者姓名
     *
     * @param createName 创建者姓名
     */
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改人
     *
     * @return edit_by - 修改人
     */
    public Long getEditBy() {
        return editBy;
    }

    /**
     * 设置修改人
     *
     * @param editBy 修改人
     */
    public void setEditBy(Long editBy) {
        this.editBy = editBy;
    }

    /**
     * 获取修改人姓名
     *
     * @return edit_name - 修改人姓名
     */
    public String getEditName() {
        return editName;
    }

    /**
     * 设置修改人姓名
     *
     * @param editName 修改人姓名
     */
    public void setEditName(String editName) {
        this.editName = editName;
    }

    /**
     * 获取修改时间
     *
     * @return edit_time - 修改时间
     */
    public Date getEditTime() {
        return editTime;
    }

    /**
     * 设置修改时间
     *
     * @param editTime 修改时间
     */
    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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
