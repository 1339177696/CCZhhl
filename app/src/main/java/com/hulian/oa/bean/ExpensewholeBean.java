package com.hulian.oa.bean;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * 报销审批总bean
 */
public class ExpensewholeBean {

    private String createBy; // 申请人id
    private String createName; // 创建人名称
    private String deptId; // 部门id
    private String deptName; // 部门名称
    private String createTime; // 创建时间
    private String receiptSum; // 创建时间
    private String money; // 总金额
    private List<ExpenseBean> expenseBeans;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReceiptSum() {
        return receiptSum;
    }

    public void setReceiptSum(String receiptSum) {
        this.receiptSum = receiptSum;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public List<ExpenseBean> getExpenseBeans() {
        return expenseBeans;
    }

    public void setExpenseBeans(List<ExpenseBean> expenseBeans) {
        this.expenseBeans = expenseBeans;
    }

}
