package com.hulian.oa.bean;

import java.io.Serializable;

/**
 * 作者：qgl 时间： 2020/5/15 14:53
 * Describe:
 */
public class SortModel implements Serializable {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;
    private String sortLetters;
    private String email;
    private String loginName;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    private String phonenumber;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    public SortModel(String name, String sortLetters, String email, String loginName,String phonenumber) {
        super();
        this.userName = name;
        this.sortLetters = sortLetters;
        this.email = email;

        this.loginName = loginName;
        this.phonenumber = phonenumber;

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SortModel() {
        super();
    }



    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
