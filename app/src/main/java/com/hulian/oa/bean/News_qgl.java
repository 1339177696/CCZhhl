package com.hulian.oa.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class News_qgl implements Serializable {
    private String createBy;
    private String journalismTitle;
    private String journalismContent;
    private String journalismImage;

    private String journalismId;
    private String createTime;
    private String isCollect;
    private String collectType;
    private String collectTypeId;
    private String collectId;

    public String getCollectTypeUserName() {
        return collectTypeUserName;
    }

    public void setCollectTypeUserName(String collectTypeUserName) {
        this.collectTypeUserName = collectTypeUserName;
    }

    public String getCollectTypeTitle() {
        return collectTypeTitle;
    }

    public void setCollectTypeTitle(String collectTypeTitle) {
        this.collectTypeTitle = collectTypeTitle;
    }

    public String getCollectTypeDetails() {
        return collectTypeDetails;
    }

    public void setCollectTypeDetails(String collectTypeDetails) {
        this.collectTypeDetails = collectTypeDetails;
    }

    public String getCollectTypeImage() {
        return collectTypeImage;
    }

    public void setCollectTypeImage(String collectTypeImage) {
        this.collectTypeImage = collectTypeImage;
    }

    private String collectTypeUserName;
    private String collectTypeTitle;
    private String collectTypeDetails;
    private String collectTypeImage;

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }


    public String getCollectTypeId() {
        return collectTypeId;
    }

    public void setCollectTypeId(String collectTypeId) {
        this.collectTypeId = collectTypeId;
    }


    public String getCollectType() {
        return collectType;
    }

    public void setCollectType(String collectType) {
        this.collectType = collectType;
    }


    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getJournalismTitle() {
        return journalismTitle;
    }

    public void setJournalismTitle(String journalismTitle) {
        this.journalismTitle = journalismTitle;
    }

    public String getJournalismContent() {
        return journalismContent;
    }

    public void setJournalismContent(String journalismContent) {
        this.journalismContent = journalismContent;
    }

    public String getJournalismImage() {
        return journalismImage;
    }

    public void setJournalismImage(String journalismImage) {
        this.journalismImage = journalismImage;
    }

    public String getJournalismId() {
        return journalismId;
    }

    public void setJournalismId(String journalismId) {
        this.journalismId = journalismId;
    }
}
