package com.hulian.oa.iac.bean;

/**
 * Created by 陈泽宇 on 2019/12/11.
 * Describe:认证
 */
public class Certification {
    private String name;//项目
    private String organization;//机构
    private String data;//日期
    private String validity;//有效期

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }
}
