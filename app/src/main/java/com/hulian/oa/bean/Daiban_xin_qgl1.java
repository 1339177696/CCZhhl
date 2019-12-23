package com.hulian.oa.bean;

import java.util.List;

/**
 * Created by qgl on 2019/12/19 16:11.
 */
public class Daiban_xin_qgl1 {


    /**
     * msg : 操作成功
     * code : 0
     * data : [{"searchValue":null,"createBy":"126","createTime":"2019-12-19 15:46:47","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"ebd33aafbceb44908a888cda896f6eba","state":"0","title":"哒","type":"1","status":"1","field1":"黄文龙","field2":"于昌旭","field3":null,"field4":null,"field5":null}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * searchValue : null
         * createBy : 126
         * createTime : 2019-12-19 15:46:47
         * updateBy : null
         * updateTime : null
         * remark : null
         * params : {}
         * id : ebd33aafbceb44908a888cda896f6eba
         * state : 0
         * title : 哒
         * type : 1
         * status : 1
         * field1 : 黄文龙
         * field2 : 于昌旭
         * field3 : null
         * field4 : null
         * field5 : null
         */

        private Object searchValue;
        private String createBy;
        private String createTime;
        private Object updateBy;
        private Object updateTime;
        private Object remark;
        private ParamsBean params;
        private String id;
        private String state;
        private String title;
        private String type;
        private String status;
        private String field1;
        private String field2;
        private Object field3;
        private Object field4;
        private Object field5;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String  name;

        public Object getF() {
            return f;
        }

        public void setF(Object f) {
            this.f = f;
        }

        private Object f;

        public Object getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(Object searchValue) {
            this.searchValue = searchValue;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public String getField2() {
            return field2;
        }

        public void setField2(String field2) {
            this.field2 = field2;
        }

        public Object getField3() {
            return field3;
        }

        public void setField3(Object field3) {
            this.field3 = field3;
        }

        public Object getField4() {
            return field4;
        }

        public void setField4(Object field4) {
            this.field4 = field4;
        }

        public Object getField5() {
            return field5;
        }

        public void setField5(Object field5) {
            this.field5 = field5;
        }

        public static class ParamsBean {
        }
    }
}
