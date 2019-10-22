package com.hulian.oa.bean;

public class Document {
    private String officialDocumentId;
    private String officialDocumentTitle;
    private String officialDocumentState;
    private String create_Time;
    private String approverIdsNames;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private String createTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    private String symbol;

    public String getInitiationType() {
        return initiationType;
    }

    public void setInitiationType(String initiationType) {
        this.initiationType = initiationType;
    }

    private String initiationType;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;

    public String getPromoterIdName() {
        return promoterIdName;
    }

    public void setPromoterIdName(String promoterIdName) {
        this.promoterIdName = promoterIdName;
    }

    private String promoterIdName;

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
