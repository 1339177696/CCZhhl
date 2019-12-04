package com.hulian.oa.qglactivity.qglbean;

/**
 * Created by qgl on 2019/12/2 16:48.
 */
public class MeBean {
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String createTime;
    private String relationId;
    private String title;
    private String content;
    private String status; // 0未查看，1以查看

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type; //


    public String getCollectionStatus() {
        return collectionStatus;
    }

    public void setCollectionStatus(String collectionStatus) {
        this.collectionStatus = collectionStatus;
    }

    private String collectionStatus; // 0未收藏，1以收藏

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id; // 0未收藏，1以收藏

    public String getReleaseDept() {
        return releaseDept;
    }

    public void setReleaseDept(String releaseDept) {
        this.releaseDept = releaseDept;
    }

    private String releaseDept; // 部门

    public String getSendPerson() {
        return sendPerson;
    }

    public void setSendPerson(String sendPerson) {
        this.sendPerson = sendPerson;
    }

    private String sendPerson; // 发件人

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    private String startDate; // 会议开始时间

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }

    private String meetingLocation; // 会议地点

}
