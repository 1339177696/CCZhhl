package com.hulian.oa.bean;
//公文
public class OfficialDocumentList {
    private String id;
    private String approveNames;
    private String releasePerson;
    private String time;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApproveNames() {
        return approveNames;
    }

    public void setApproveNames(String approveNames) {
        this.approveNames = approveNames;
    }

    public String getReleasePerson() {
        return releasePerson;
    }

    public void setReleasePerson(String releasePerson) {
        this.releasePerson = releasePerson;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
