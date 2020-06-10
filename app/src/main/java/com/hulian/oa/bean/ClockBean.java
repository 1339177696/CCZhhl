package com.hulian.oa.bean;

/**
 * 作者：qgl 时间： 2020/5/11 10:38
 * Describe: 考勤统计bean
 */
public class ClockBean {

    private String createTime; // 打卡天
    private String registerUpTime; // 上班时间
    private String registerDownTime; // 下班时间
    private String type; // 数据类型
    private String startTime; // 请假开始时间
    private String endTime; // 请假结束时间
    private String duration; // 请假天数
    private String registerUpState; // 缺勤状态上午
    private String registerDownState; // 缺勤状态下午
    public String getRegisterUpState() {
        return registerUpState;
    }

    public void setRegisterUpState(String registerUpState) {
        this.registerUpState = registerUpState;
    }

    public String getRegisterDownState() {
        return registerDownState;
    }

    public void setRegisterDownState(String registerDownState) {
        this.registerDownState = registerDownState;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRegisterUpTime() {
        return registerUpTime;
    }

    public void setRegisterUpTime(String registerUpTime) {
        this.registerUpTime = registerUpTime;
    }

    public String getRegisterDownTime() {
        return registerDownTime;
    }

    public void setRegisterDownTime(String registerDownTime) {
        this.registerDownTime = registerDownTime;
    }

}
