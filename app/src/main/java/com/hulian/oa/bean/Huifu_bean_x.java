package com.hulian.oa.bean;

import java.util.List;

/**
 * Created by czy on 2019/8/3 15:35.
 */
public class Huifu_bean_x
{
    /**
     * msg : 成功
     * code : 0
     * data : {"sum":"0/1","list":[{"searchValue":null,"createBy":null,"createTime":"2019-08-03 15:08:37","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"39166ce0fb534a28a9269628dd404aba","proid":"41a705d744504c4e869db76eb21b6fa9","content":null,"respondent":"黄文龙","picture":"https://abc.cczhhl.cn/profile/2019/08/03/fa703608f39bc40cf73520d9ad7cbe45.jpg","spare1":null,"spare2":null,"spare3":null,"spare4":null,"spare5":null}],"object":{"searchValue":null,"createBy":"黄文龙","createTime":"2019-08-03 11:14:03","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"41a705d744504c4e869db76eb21b6fa9","title":"大气","details":"的","executor":"黄文龙,","startTime":"2019-08-03 11:14:03","endTime":"2019-08-03 11:13:51","copier":"张博","taskReminder":"不提醒","completion":"0"}}
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
         * sum : 0/1
         * list : [{"searchValue":null,"createBy":null,"createTime":"2019-08-03 15:08:37","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"39166ce0fb534a28a9269628dd404aba","proid":"41a705d744504c4e869db76eb21b6fa9","content":null,"respondent":"黄文龙","picture":"https://abc.cczhhl.cn/profile/2019/08/03/fa703608f39bc40cf73520d9ad7cbe45.jpg","spare1":null,"spare2":null,"spare3":null,"spare4":null,"spare5":null}]
         * object : {"searchValue":null,"createBy":"黄文龙","createTime":"2019-08-03 11:14:03","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"41a705d744504c4e869db76eb21b6fa9","title":"大气","details":"的","executor":"黄文龙,","startTime":"2019-08-03 11:14:03","endTime":"2019-08-03 11:13:51","copier":"张博","taskReminder":"不提醒","completion":"0"}
         */

        private String sum;
        private ObjectBean object;
        private List<ListBean> list;

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public ObjectBean getObject() {
            return object;
        }

        public void setObject(ObjectBean object) {
            this.object = object;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ObjectBean {
            /**
             * searchValue : null
             * createBy : 黄文龙
             * createTime : 2019-08-03 11:14:03
             * updateBy : null
             * updateTime : null
             * remark : null
             * params : {}
             * id : 41a705d744504c4e869db76eb21b6fa9
             * title : 大气
             * details : 的
             * executor : 黄文龙,
             * startTime : 2019-08-03 11:14:03
             * endTime : 2019-08-03 11:13:51
             * copier : 张博
             * taskReminder : 不提醒
             * completion : 0
             */

            private Object searchValue;
            private String createBy;
            private String createTime;
            private Object updateBy;
            private Object updateTime;
            private Object remark;
            private ParamsBean params;
            private String id;
            private String title;
            private String details;
            private String executor;
            private String startTime;
            private String endTime;
            private String copier;
            private String taskReminder;
            private String completion;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }

            public String getExecutor() {
                return executor;
            }

            public void setExecutor(String executor) {
                this.executor = executor;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getCopier() {
                return copier;
            }

            public void setCopier(String copier) {
                this.copier = copier;
            }

            public String getTaskReminder() {
                return taskReminder;
            }

            public void setTaskReminder(String taskReminder) {
                this.taskReminder = taskReminder;
            }

            public String getCompletion() {
                return completion;
            }

            public void setCompletion(String completion) {
                this.completion = completion;
            }

            public static class ParamsBean {
            }
        }

        public static class ListBean {
            /**
             * searchValue : null
             * createBy : null
             * createTime : 2019-08-03 15:08:37
             * updateBy : null
             * updateTime : null
             * remark : null
             * params : {}
             * id : 39166ce0fb534a28a9269628dd404aba
             * proid : 41a705d744504c4e869db76eb21b6fa9
             * content : null
             * respondent : 黄文龙
             * picture : https://abc.cczhhl.cn/profile/2019/08/03/fa703608f39bc40cf73520d9ad7cbe45.jpg
             * spare1 : null
             * spare2 : null
             * spare3 : null
             * spare4 : null
             * spare5 : null
             */

            private Object searchValue;
            private Object createBy;
            private String createTime;
            private Object updateBy;
            private Object updateTime;
            private Object remark;
            private ParamsBeanX params;
            private String id;
            private String proid;
            private Object content;
            private String respondent;
            private String picture;
            private Object spare1;
            private Object spare2;
            private Object spare3;
            private Object spare4;
            private Object spare5;

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

            public ParamsBeanX getParams() {
                return params;
            }

            public void setParams(ParamsBeanX params) {
                this.params = params;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getProid() {
                return proid;
            }

            public void setProid(String proid) {
                this.proid = proid;
            }

            public Object getContent() {
                return content;
            }

            public void setContent(Object content) {
                this.content = content;
            }

            public String getRespondent() {
                return respondent;
            }

            public void setRespondent(String respondent) {
                this.respondent = respondent;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public Object getSpare1() {
                return spare1;
            }

            public void setSpare1(Object spare1) {
                this.spare1 = spare1;
            }

            public Object getSpare2() {
                return spare2;
            }

            public void setSpare2(Object spare2) {
                this.spare2 = spare2;
            }

            public Object getSpare3() {
                return spare3;
            }

            public void setSpare3(Object spare3) {
                this.spare3 = spare3;
            }

            public Object getSpare4() {
                return spare4;
            }

            public void setSpare4(Object spare4) {
                this.spare4 = spare4;
            }

            public Object getSpare5() {
                return spare5;
            }

            public void setSpare5(Object spare5) {
                this.spare5 = spare5;
            }

            public static class ParamsBeanX {
            }
        }
    }
}
