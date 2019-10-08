package com.hulian.oa.bean;

public class Document {
    private String officialDocumentId;
    private String officialDocumentTitle;
    private String officialDocumentState;
    private String create_Time;
    private String approverIdsNames;

    public String getApproverIdsNames() {
        return approverIdsNames;
    }

    public void setApproverIdsNames(String approverIdsNames) {
        this.approverIdsNames = approverIdsNames;
    }

    public String getOfficialDocumentId() {
        return officialDocumentId;
    }

    public void setOfficialDocumentId(String officialDocumentId) {
        this.officialDocumentId = officialDocumentId;
    }

    public String getOfficialDocumentTitle() {
        return officialDocumentTitle;
    }

    public void setOfficialDocumentTitle(String officialDocumentTitle) {
        this.officialDocumentTitle = officialDocumentTitle;
    }

    public String getOfficialDocumentState() {
        return officialDocumentState;
    }

    public void setOfficialDocumentState(String officialDocumentState) {
        this.officialDocumentState = officialDocumentState;
    }

    public String getCreate_Time() {
        return create_Time;
    }

    public void setCreate_Time(String create_Time) {
        this.create_Time = create_Time;
    }
}
