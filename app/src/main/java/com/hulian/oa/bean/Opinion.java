package com.hulian.oa.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 陈泽宇 on 2020/3/16.
 * Describe:
 */
public class Opinion {

    @SerializedName("opinionReplePName")
    private String name;
    @SerializedName("createTime")
    private String time;
    @SerializedName("opinionContent")
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
