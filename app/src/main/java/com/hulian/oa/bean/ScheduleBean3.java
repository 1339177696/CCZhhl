package com.hulian.oa.bean;

public class ScheduleBean3 {
    private String scheduleTimeBegin;
    private String scheduleTimeEnd;
    private String timeTitle;
    private String scheduleContent;
    private String id;
    private boolean hasContent;
    private boolean isNow;
    private String  timeNow;
    private String isToday;
    private String warnTime;

    public String getWarnTime() {
        return warnTime;
    }

    public void setWarnTime(String warnTime) {
        this.warnTime = warnTime;
    }

    public String getQufen() {
        return qufen;
    }

    public void setQufen(String qufen) {
        this.qufen = qufen;
    }

    private String qufen;

    public String getIsToday() {
        return isToday;
    }

    public void setIsToday(String isToday) {
        this.isToday = isToday;
    }


    public String getTimeNow() {
        return timeNow;
    }

    public void setTimeNow(String timeNow) {
        this.timeNow = timeNow;
    }

    public boolean isNow() {
        return isNow;
    }

    public void setNow(boolean now) {
        isNow = now;
    }

    public boolean isHasContent() {
        return hasContent;
    }

    public void setHasContent(boolean hasContent) {
        this.hasContent = hasContent;
    }

    public String getScheduleTimeBegin() {
        return scheduleTimeBegin;
    }

    public void setScheduleTimeBegin(String scheduleTimeBegin) {
        this.scheduleTimeBegin = scheduleTimeBegin;
    }

    public String getScheduleTimeEnd() {
        return scheduleTimeEnd;
    }

    public void setScheduleTimeEnd(String scheduleTimeEnd) {
        this.scheduleTimeEnd = scheduleTimeEnd;
    }

    public String getTimeTitle() {
        return timeTitle;
    }

    public void setTimeTitle(String timeTitle) {
        this.timeTitle = timeTitle;
    }

    public String getScheduleContent() {
        return scheduleContent;
    }

    public void setScheduleContent(String scheduleContent) {
        this.scheduleContent = scheduleContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
