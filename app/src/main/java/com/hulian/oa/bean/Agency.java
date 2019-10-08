package com.hulian.oa.bean;

import java.util.List;
public class Agency {
    private List<WorkReimbursementList> workReimbursementList;
    private List<WorkLeaveList> workLeaveList;
    private List<WorkCoordinationReleaseList> workCoordinationReleaseList;
    private List<OfficialDocumentList> officialDocumentList;
    private List<MeetingList> meetingList;
    private List<InstructionsList> instructionsList;

    public List<WorkReimbursementList> getWorkReimbursementList() {
        return workReimbursementList;
    }

    public void setWorkReimbursementList(List<WorkReimbursementList> workReimbursementList) {
        this.workReimbursementList = workReimbursementList;
    }

    public List<WorkLeaveList> getWorkLeaveList() {
        return workLeaveList;
    }

    public void setWorkLeaveList(List<WorkLeaveList> workLeaveList) {
        this.workLeaveList = workLeaveList;
    }

    public List<WorkCoordinationReleaseList> getWorkCoordinationReleaseList() {
        return workCoordinationReleaseList;
    }

    public void setWorkCoordinationReleaseList(List<WorkCoordinationReleaseList> workCoordinationReleaseList) {
        this.workCoordinationReleaseList = workCoordinationReleaseList;
    }

    public List<OfficialDocumentList> getOfficialDocumentList() {
        return officialDocumentList;
    }

    public void setOfficialDocumentList(List<OfficialDocumentList> officialDocumentList) {
        this.officialDocumentList = officialDocumentList;
    }

    public List<MeetingList> getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(List<MeetingList> meetingList) {
        this.meetingList = meetingList;
    }

    public List<InstructionsList> getInstructionsList() {
        return instructionsList;
    }

    public void setInstructionsList(List<InstructionsList> instructionsList) {
        this.instructionsList = instructionsList;
    }
}
