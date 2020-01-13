package com.hulian.oa.bean;

import java.util.List;

/**
 * Created by qgl on 2020/1/13 10:54.
 */
public class OutIndexBean {
    /**
     * total : 2
     * rows : [{"searchValue":null,"createBy":null,"createTime":"2020-01-13 10:43:58","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"b787d36a950d4476b8645b76869dd8e5","sendMail":"hwl@zhhl.com","sendMailPerson":"黄文龙","reMail":"lx@zhhl.com","reMailPerson":"刘欣","ccMail":"tl@zhhl.com","ccMailPerson":"佟璐","bccMail":null,"bccMailPerson":null,"title":"应用","attachment":null,"mailContent":"哦哦哦哦","type":"1","mailPassword":null,"readPerson":"刘欣"},{"searchValue":null,"createBy":null,"createTime":"2020-01-10 10:52:29","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"1636d83354964424a74d87ac0888053b","sendMail":"hwl@zhhl.com","sendMailPerson":"黄文龙","reMail":"wjj@zhhl.com","reMailPerson":"王俊杰","ccMail":null,"ccMailPerson":null,"bccMail":null,"bccMailPerson":null,"title":"做事","attachment":null,"mailContent":"多做事","type":"1","mailPassword":null,"readPerson":"王俊杰"}]
     * code : 0
     */

    private int total;
    private int code;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * searchValue : null
         * createBy : null
         * createTime : 2020-01-13 10:43:58
         * updateBy : null
         * updateTime : null
         * remark : null
         * params : {}
         * id : b787d36a950d4476b8645b76869dd8e5
         * sendMail : hwl@zhhl.com
         * sendMailPerson : 黄文龙
         * reMail : lx@zhhl.com
         * reMailPerson : 刘欣
         * ccMail : tl@zhhl.com
         * ccMailPerson : 佟璐
         * bccMail : null
         * bccMailPerson : null
         * title : 应用
         * attachment : null
         * mailContent : 哦哦哦哦
         * type : 1
         * mailPassword : null
         * readPerson : 刘欣
         */

        private Object searchValue;
        private Object createBy;
        private String createTime;
        private Object updateBy;
        private Object updateTime;
        private Object remark;
        private ParamsBean params;
        private String id;
        private String sendMail;
        private String sendMailPerson;
        private String reMail;
        private String reMailPerson;
        private String ccMail;
        private String ccMailPerson;
        private Object bccMail;
        private Object bccMailPerson;
        private String title;
        private Object attachment;
        private String mailContent;
        private String type;
        private Object mailPassword;
        private String readPerson;

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

        public String getSendMail() {
            return sendMail;
        }

        public void setSendMail(String sendMail) {
            this.sendMail = sendMail;
        }

        public String getSendMailPerson() {
            return sendMailPerson;
        }

        public void setSendMailPerson(String sendMailPerson) {
            this.sendMailPerson = sendMailPerson;
        }

        public String getReMail() {
            return reMail;
        }

        public void setReMail(String reMail) {
            this.reMail = reMail;
        }

        public String getReMailPerson() {
            return reMailPerson;
        }

        public void setReMailPerson(String reMailPerson) {
            this.reMailPerson = reMailPerson;
        }

        public String getCcMail() {
            return ccMail;
        }

        public void setCcMail(String ccMail) {
            this.ccMail = ccMail;
        }

        public String getCcMailPerson() {
            return ccMailPerson;
        }

        public void setCcMailPerson(String ccMailPerson) {
            this.ccMailPerson = ccMailPerson;
        }

        public Object getBccMail() {
            return bccMail;
        }

        public void setBccMail(Object bccMail) {
            this.bccMail = bccMail;
        }

        public Object getBccMailPerson() {
            return bccMailPerson;
        }

        public void setBccMailPerson(Object bccMailPerson) {
            this.bccMailPerson = bccMailPerson;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getAttachment() {
            return attachment;
        }

        public void setAttachment(Object attachment) {
            this.attachment = attachment;
        }

        public String getMailContent() {
            return mailContent;
        }

        public void setMailContent(String mailContent) {
            this.mailContent = mailContent;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getMailPassword() {
            return mailPassword;
        }

        public void setMailPassword(Object mailPassword) {
            this.mailPassword = mailPassword;
        }

        public String getReadPerson() {
            return readPerson;
        }

        public void setReadPerson(String readPerson) {
            this.readPerson = readPerson;
        }

        public static class ParamsBean {
        }
    }
}
