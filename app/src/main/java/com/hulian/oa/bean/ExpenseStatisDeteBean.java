package com.hulian.oa.bean;

/**
 * 作者：qgl 时间： 2020/8/26 08:53
 * Describe:
 */
public class ExpenseStatisDeteBean {

    private String msg; // 总金额
    private String createBy; // 登陆人
    private String money; // 金额
    private String cause; // 备注
    private String id; //

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private String createTime; //时间

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
