package com.hulian.oa.bean;

import java.io.Serializable;

public class News implements Serializable {
    private String createBy;
    private String journalismTitle;
    private String journalismContent;
    private String journalismImage;
    private String journalismId;
    private String createTime;

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
