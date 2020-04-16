package com.hulian.oa.bean;

import java.util.List;

public class User {
    String remark;
    private  String userName;
    private String userId;
    private String  password;
    private  Dept dept;
    private String isLead;//权限状态（0：领导，1：职员，2：多岗位）
    private String deptId;
    private String email;
    private List<Roles> roles;
    private String rolesStr = "";
    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    //所有权限字段拼接在一起
    public String getRolesStr() {
        for (Roles role : roles) {
            rolesStr = rolesStr + role.getRoleKey();
        }
        return rolesStr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsLead() {
        return isLead;
    }

    public void setIsLead(String isLead) {
        this.isLead = isLead;
    }
}
