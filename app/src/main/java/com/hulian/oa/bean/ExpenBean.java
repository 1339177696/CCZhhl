package com.hulian.oa.bean;

/**
 * 作者：qgl 时间： 2020/7/16 08:27
 * Describe: 报销详情
 */
public class ExpenBean {


    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    private String approveType;

    public String getLineMoney() {
        return lineMoney;
    }

    public void setLineMoney(String lineMoney) {
        this.lineMoney = lineMoney;
    }

    private String lineMoney;

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    private String cause;

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    private String files;
}
