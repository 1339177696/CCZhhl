package com.hulian.oa.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 陈泽宇 on 2020/4/21
 * Describe: 会议
 */
public class VideoMeeting {

    private String month;//月
    private String day;//日



    private String Year;//年
    @SerializedName("initiatorName")
    private String title;//标题
    @SerializedName("meetingid")
    private String number;//会议号
    private String startTime;//会议开始时间
    private String stopTime;//会议结束时间
    @SerializedName("meetingStatus")
    private String state;//会议状态
    @SerializedName("starttime")
    private String startTimeStr = "";//会议开始时间字符串
    @SerializedName("endtime")
    private String stopTimeStr = "";//会议结束时间字符串
    private String participant; // 参会人
    private String meetinglName; // 房间号

    public String getMeetinglName() {
        return meetinglName;
    }

    public void setMeetinglName(String meetinglName) {
        this.meetinglName = meetinglName;
    }


    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }


    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getStopTimeStr() {
        return stopTimeStr;
    }

    public void setStopTimeStr(String stopTimeStr) {
        this.stopTimeStr = stopTimeStr;
    }

    public String getMonth() {
        return getMouth(getStartTimeStr());
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return getDayStr(getStartTimeStr());
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStartTime() {
        return getstartimestr(getStartTimeStr());
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {

        return getStoptime(getStopTimeStr());
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getYear() {
        return getYear(getStartTimeStr());
    }

    public void setYear(String year) {
        Year = year;
    }

    // 截取月
    private String getMouth(String startTimeStr){
        if (startTimeStr.length()>5){
        return startTimeStr.substring(5,7);
        }
        return "";
    }
    // 截取日
    private String getDayStr(String startTime){
        if (startTime.length()>8){
            return startTime.substring(8,10);
        }
        return "";
    }
    // 截取年
    private String getYear(String startTime){
        if (startTime.length()>10){
            return startTime.substring(0,10);
        }
        return "";
    }

    //截取开始时间
    private String getstartimestr(String startTime){
        if (startTime.length()>10){
            return startTime.substring(11,16);
        }
        return "";
    }

    //截取结束时间
    private String getStoptime(String stoptime){
        if (!TextUtils.isEmpty(stoptime))
            if (stoptime.length()>10){
                return stoptime.substring(11,16);
            }else {
                return "";
            }

        return "";
    }


}
