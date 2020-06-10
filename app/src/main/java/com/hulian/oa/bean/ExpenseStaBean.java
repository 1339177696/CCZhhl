package com.hulian.oa.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：qgl 时间： 2020/5/7 14:53
 * Describe: 报销统计bean
 */
public class ExpenseStaBean {

    @SerializedName("initiatorName")
    private String state; // 类型
    @SerializedName("initiatorName")
    private String num_per; // 百分比
    @SerializedName("initiatorName")
    private String num; // 金额

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNum_per() {
        return num_per;
    }

    public void setNum_per(String num_per) {
        this.num_per = num_per;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

}
