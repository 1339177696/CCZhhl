package com.hulian.oa.bean;

import com.hulian.oa.net.Utils;

import java.util.List;

/**
 * Created by czy on 2019/8/5 9:42.
 */
public class Meeting {

    private String id;//	会议表id
    private String meetingTheme;// 	会议主题
    private String meetingTime;//会议时间
    private String meetingRoomLocation;//会议室位置
    private String meetingTimeBegin;
    private String meetingTimeEnd;
    private String signType;
    private String meetingRoomName; // 会议室名称


    public String getMeetingRoomName() {
        return meetingRoomName;
    }

    public void setMeetingRoomName(String meetingRoomName) {
        this.meetingRoomName = meetingRoomName;
    }


    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getMeetingTimeEnd() {
        return meetingTimeEnd;
    }

    public void setMeetingTimeEnd(String meetingTimeEnd) {
        this.meetingTimeEnd = meetingTimeEnd;
    }

    public String getMeetingTimeBegin() {
        return meetingTimeBegin;
    }

    public void setMeetingTimeBegin(String meetingTimeBegin) {
        this.meetingTimeBegin = meetingTimeBegin;
    }

    private List<Participants> participants;

    public List<Participants> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participants> participants) {
        this.participants = participants;
    }


    public class Participants{

        private String id;
        private String participantName;//参加会议人员姓名
        private String signStatus;//参加会议人员签到状态
        private String participantId;

        public String getParticipantId() {
            return participantId;
        }

        public void setParticipantId(String participantId) {
            this.participantId = participantId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParticipantName() {
            return participantName;
        }

        public void setParticipantName(String participantName) {
            this.participantName = participantName;
        }

        public String getSignStatus() {
            return signStatus;
        }

        public void setSignStatus(String signStatus) {
            this.signStatus = signStatus;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeetingTheme() {
        return meetingTheme;
    }

    public void setMeetingTheme(String meetingTheme) {
        this.meetingTheme = meetingTheme;
    }

    public String getMeetingTime() {
      //  return Utils.getTime(meetingTime);
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getMeetingRoomLocation() {
        return meetingRoomLocation;
    }

    public void setMeetingRoomLocation(String meetingRoomLocation) {
        this.meetingRoomLocation = meetingRoomLocation;
    }
}
