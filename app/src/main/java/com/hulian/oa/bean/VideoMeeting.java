package com.hulian.oa.bean;

/**
 * Created by 陈泽宇 on 2020/4/21
 * Describe: 会议
 */
public class VideoMeeting {

    private String month;//月
    private String day;//日
    private String title;//标题
    private String number;//会议号
    private String startTime;//会议开始时间
    private String stopTime;//会议结束时间
    private String state;//会议状态

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
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
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
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
}
