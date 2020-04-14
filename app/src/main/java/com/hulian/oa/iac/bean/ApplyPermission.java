package com.hulian.oa.iac.bean;

/**
 * Created by 陈泽宇 on 2019/12/10.
 * Describe:申请许可
 */
public class ApplyPermission {
    private String sort;//类别
    private String children;//子项
    private String level;//级别
    private String type;//品种

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
