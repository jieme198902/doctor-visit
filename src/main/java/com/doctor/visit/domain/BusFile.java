package com.doctor.visit.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "bus_file")
public class BusFile implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 文件磁盘相对路径
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * 业务表名
     */
    private String bus;

    /**
     * 业务表中的id
     */
    @Column(name = "bus_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long busId;

    /**
     * 文件类型：0图片，
     */
    @Column(name = "file_type")
    private String fileType;

    @Transient
    private boolean delBefore = true;

    private static final long serialVersionUID = 1L;

    public boolean isDelBefore() {
        return delBefore;
    }

    public void setDelBefore(boolean delBefore) {
        this.delBefore = delBefore;
    }

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
     * 获取文件磁盘相对路径
     *
     * @return file_path - 文件磁盘相对路径
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置文件磁盘相对路径
     *
     * @param filePath 文件磁盘相对路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取业务表名
     *
     * @return bus - 业务表名
     */
    public String getBus() {
        return bus;
    }

    /**
     * 设置业务表名
     *
     * @param bus 业务表名
     */
    public void setBus(String bus) {
        this.bus = bus;
    }

    /**
     * 获取业务表中的id
     *
     * @return bus_id - 业务表中的id
     */
    public Long getBusId() {
        return busId;
    }

    /**
     * 设置业务表中的id
     *
     * @param busId 业务表中的id
     */
    public void setBusId(Long busId) {
        this.busId = busId;
    }

    /**
     * 获取文件类型：0图片，
     *
     * @return file_type - 文件类型：0图片，
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置文件类型：0图片，
     *
     * @param fileType 文件类型：0图片，
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
