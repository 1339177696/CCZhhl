package com.hulian.oa.bean;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

public class ExpenseBean {
    private String index;//索引id (id值为list的大小，添加多个空数据对象时移除时会导致移除最后一个，不按照位置移除)
    private String expense_reason;//报销事由
    private String expense_money;//报销金额
    private String expense_legend;//费用说明
    private List<LocalMedia> list_invoice;//发票说明

    public String getReason_num() {
        return reason_num;
    }

    public void setReason_num(String reason_num) {
        this.reason_num = reason_num;
    }

    private String reason_num;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getExpense_reason() {
        return expense_reason;
    }

    public void setExpense_reason(String expense_reason) {
        this.expense_reason = expense_reason;
    }

    public String getExpense_money() {
        return expense_money;
    }

    public void setExpense_money(String expense_money) {
        this.expense_money = expense_money;
    }

    public String getExpense_legend() {
        return expense_legend;
    }

    public void setExpense_legend(String expense_legend) {
        this.expense_legend = expense_legend;
    }

    public List<LocalMedia> getList_invoice() {
        return list_invoice;
    }

    public void setList_invoice(List<LocalMedia> list_invoice) {
        this.list_invoice = list_invoice;
    }
}
