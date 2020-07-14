package com.doctor.visit.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "bus_dict")
public class BusDict implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 字典名称
     */
    @Column(name = "dic_name")
    private String dicName;

    /**
     * 字典值
     */
    @Column(name = "dic_value")
    private String dicValue;

    /**
     * 字典类型
     */
    @Column(name = "dic_type")
    private String dicType;

    /**
     * 排序
     */
    @Column(name = "dic_sort")
    private Integer dicSort;

    /**
     * 父级id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;

    /**
     * 是否启用：1是，0否
     */
    private String enable;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取字典名称
     *
     * @return dic_name - 字典名称
     */
    public String getDicName() {
        return dicName;
    }

    /**
     * 设置字典名称
     *
     * @param dicName 字典名称
     */
    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    /**
     * 获取字典值
     *
     * @return dic_value - 字典值
     */
    public String getDicValue() {
        return dicValue;
    }

    /**
     * 设置字典值
     *
     * @param dicValue 字典值
     */
    public void setDicValue(String dicValue) {
        this.dicValue = dicValue;
    }

    /**
     * 获取字典类型
     *
     * @return dic_type - 字典类型
     */
    public String getDicType() {
        return dicType;
    }

    /**
     * 设置字典类型
     *
     * @param dicType 字典类型
     */
    public void setDicType(String dicType) {
        this.dicType = dicType;
    }

    /**
     * 获取排序
     *
     * @return dic_sort - 排序
     */
    public Integer getDicSort() {
        return dicSort;
    }

    /**
     * 设置排序
     *
     * @param dicSort 排序
     */
    public void setDicSort(Integer dicSort) {
        this.dicSort = dicSort;
    }

    /**
     * 获取父级id
     *
     * @return pid - 父级id
     */
    public Long getPid() {
        return pid;
    }

    /**
     * 设置父级id
     *
     * @param pid 父级id
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * 获取是否启用：1是，0否
     *
     * @return enable - 是否启用：1是，0否
     */
    public String getEnable() {
        return enable;
    }

    /**
     * 设置是否启用：1是，0否
     *
     * @param enable 是否启用：1是，0否
     */
    public void setEnable(String enable) {
        this.enable = enable;
    }
}
