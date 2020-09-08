package com.hulian.oa.bean;

/**
 * 作者：qgl 时间： 2020/7/3 09:31
 * Describe: 消息bean
 */
public class Messagebean {
    private String type;  // 类型
    private String count;  // 长度
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
