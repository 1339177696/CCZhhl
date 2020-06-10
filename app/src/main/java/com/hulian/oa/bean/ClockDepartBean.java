package com.hulian.oa.bean;

/**
 * 作者：qgl 时间： 2020/5/11 14:38
 * Describe: 考勤部门查询Bean
 */
public class ClockDepartBean {
    private String userName ;
    private String deptName ;
    private String type ;
    private String createBy;
    private String severalDay; // 天数


    public String getSeveralDay() {
        return severalDay;
    }

    public void setSeveralDay(String severalDay) {
        this.severalDay = severalDay;
    }


    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
