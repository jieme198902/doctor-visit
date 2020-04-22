package com.doctor.visit.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Databasechangelog implements Serializable {
    @Column(name = "ID")
    private String id;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "FILENAME")
    private String filename;

    @Column(name = "DATEEXECUTED")
    private Date dateexecuted;

    @Column(name = "ORDEREXECUTED")
    private Integer orderexecuted;

    @Column(name = "EXECTYPE")
    private String exectype;

    @Column(name = "MD5SUM")
    private String md5sum;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "TAG")
    private String tag;

    @Column(name = "LIQUIBASE")
    private String liquibase;

    @Column(name = "CONTEXTS")
    private String contexts;

    @Column(name = "LABELS")
    private String labels;

    @Column(name = "DEPLOYMENT_ID")
    private String deploymentId;

    private static final long serialVersionUID = 1L;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return AUTHOR
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return FILENAME
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return DATEEXECUTED
     */
    public Date getDateexecuted() {
        return dateexecuted;
    }

    /**
     * @param dateexecuted
     */
    public void setDateexecuted(Date dateexecuted) {
        this.dateexecuted = dateexecuted;
    }

    /**
     * @return ORDEREXECUTED
     */
    public Integer getOrderexecuted() {
        return orderexecuted;
    }

    /**
     * @param orderexecuted
     */
    public void setOrderexecuted(Integer orderexecuted) {
        this.orderexecuted = orderexecuted;
    }

    /**
     * @return EXECTYPE
     */
    public String getExectype() {
        return exectype;
    }

    /**
     * @param exectype
     */
    public void setExectype(String exectype) {
        this.exectype = exectype;
    }

    /**
     * @return MD5SUM
     */
    public String getMd5sum() {
        return md5sum;
    }

    /**
     * @param md5sum
     */
    public void setMd5sum(String md5sum) {
        this.md5sum = md5sum;
    }

    /**
     * @return DESCRIPTION
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return COMMENTS
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return TAG
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return LIQUIBASE
     */
    public String getLiquibase() {
        return liquibase;
    }

    /**
     * @param liquibase
     */
    public void setLiquibase(String liquibase) {
        this.liquibase = liquibase;
    }

    /**
     * @return CONTEXTS
     */
    public String getContexts() {
        return contexts;
    }

    /**
     * @param contexts
     */
    public void setContexts(String contexts) {
        this.contexts = contexts;
    }

    /**
     * @return LABELS
     */
    public String getLabels() {
        return labels;
    }

    /**
     * @param labels
     */
    public void setLabels(String labels) {
        this.labels = labels;
    }

    /**
     * @return DEPLOYMENT_ID
     */
    public String getDeploymentId() {
        return deploymentId;
    }

    /**
     * @param deploymentId
     */
    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }
}