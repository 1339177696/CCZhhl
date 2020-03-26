package com.hulian.oa.bean;

/**
 * Created by qgl on 2020/3/25 10:27.
 */
public class Res {

    /**
     * msg : 操作成功
     * code : 0
     * data : {"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"1d64255a43a74417bb3c3a186a9bb153","registerContent":"最终测试时间123","upTime":"09:00","downTime":"17:00","registerAddress":"力旺广场-B栋写字楼","registerCoordinate":"125.290623,43.840772","distance":"500","rxhRule":"牛逼"}
     */

    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * searchValue : null
         * createBy : null
         * createTime : null
         * updateBy : null
         * updateTime : null
         * remark : null
         * params : {}
         * id : 1d64255a43a74417bb3c3a186a9bb153
         * registerContent : 最终测试时间123
         * upTime : 09:00
         * downTime : 17:00
         * registerAddress : 力旺广场-B栋写字楼
         * registerCoordinate : 125.290623,43.840772
         * distance : 500
         * rxhRule : 牛逼
         */

        private Object searchValue;
        private Object createBy;
        private Object createTime;
        private Object updateBy;
        private Object updateTime;
        private Object remark;
        private ParamsBean params;
        private String id;
        private String registerContent;
        private String upTime;
        private String downTime;
        private String registerAddress;
        private String registerCoordinate;
        private String distance;
        private String rxhRule;

        public Object getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(Object searchValue) {
            this.searchValue = searchValue;
        }

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public ParamsBean getParams() {
            return params;
        }

        public void setParams(ParamsBean params) {
            this.params = params;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRegisterContent() {
            return registerContent;
        }

        public void setRegisterContent(String registerContent) {
            this.registerContent = registerContent;
        }

        public String getUpTime() {
            return upTime;
        }

        public void setUpTime(String upTime) {
            this.upTime = upTime;
        }

        public String getDownTime() {
            return downTime;
        }

        public void setDownTime(String downTime) {
            this.downTime = downTime;
        }

        public String getRegisterAddress() {
            return registerAddress;
        }

        public void setRegisterAddress(String registerAddress) {
            this.registerAddress = registerAddress;
        }

        public String getRegisterCoordinate() {
            return registerCoordinate;
        }

        public void setRegisterCoordinate(String registerCoordinate) {
            this.registerCoordinate = registerCoordinate;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getRxhRule() {
            return rxhRule;
        }

        public void setRxhRule(String rxhRule) {
            this.rxhRule = rxhRule;
        }

        public static class ParamsBean {
        }
    }
}
