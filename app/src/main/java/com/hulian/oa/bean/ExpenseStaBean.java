package com.hulian.oa.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：qgl 时间： 2020/5/7 14:53
 * Describe: 报销统计bean
 */
public class ExpenseStaBean {

    @SerializedName("typeName")
    private String state; // 类型名称
    @SerializedName("bfb")
    private String num_per; // 百分比
    @SerializedName("sum")
    private String num; // 金额

    @SerializedName("type")
    private String type; // 类型

    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    @SerializedName("approveType")
    private String approveType; // 类型

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @SerializedName("money")
    private String money; // 类型




    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
