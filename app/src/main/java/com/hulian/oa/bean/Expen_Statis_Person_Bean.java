package com.hulian.oa.bean;

/**
 * 作者：qgl 时间： 2020/8/26 11:04
 * Describe:
 */
public class Expen_Statis_Person_Bean {
    private String userName;  // 姓名
    private String money;  // 总金额
    private String createTime;  // 时间
    private String userId;  // ID
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
