package com.hulian.oa.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 陈泽宇 on 2020/3/11.
 * Describe: 汇报
 */
public class Report implements Serializable {
    @SerializedName("reportType")
    private String type;//类别 日报 月报 周报
    @SerializedName("createByName")
    private String name;
    private String finishWork;//完成的工作
    @SerializedName("unfinishedWork")
    private String unFinishWork;//未完成的工作
    @SerializedName("createTime")
    private String time;//提交时间

    private String id;
    private String createBy;//汇报创建人
    @SerializedName("tomorrowWorkPlan")
    private String planWork;//计划任务

    private String coordinateWork;//协调的工作

    private String receivePersonName;//汇报接收人姓名

    public String getPlanWork() {
        return planWork;
    }

    public void setPlanWork(String planWork) {
        this.planWork = planWork;
    }

    public String getCoordinateWork() {
        return coordinateWork;
    }

    public void setCoordinateWork(String coordinateWork) {
        this.coordinateWork = coordinateWork;
    }

    public String getReceivePersonName() {
        return receivePersonName;
    }

    public void setReceivePersonName(String receivePersonName) {
        this.receivePersonName = receivePersonName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFinishWork() {
        return finishWork;
    }

    public void setFinishWork(String finishWork) {
        this.finishWork = finishWork;
    }

    public String getUnFinishWork() {
        return unFinishWork;
    }

    public void setUnFinishWork(String unFinishWork) {
        this.unFinishWork = unFinishWork;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
