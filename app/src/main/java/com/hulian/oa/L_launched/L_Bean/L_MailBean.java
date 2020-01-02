package com.hulian.oa.L_launched.L_Bean;

import java.util.List;

/**
 * Created by qgl on 2020/1/2 11:21.
 */
public class L_MailBean {

    /**
     * total : 2
     * rows : [{"searchValue":null,"createBy":null,"createTime":"2019-11-28 09:28:16","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"939e2af9294b4406b2e96ff2f0f5e7fa","sendMail":"hwl@zhhl.com","sendMailPerson":"黄文龙","reMail":"wfy@zhhl.com","reMailPerson":"王飞越","ccMail":null,"ccMailPerson":null,"bccMail":null,"bccMailPerson":null,"title":"出入境内外网站","attachment":"b1370948c47446d8ac0faba5d4b241be","mailContent":"<p>出入境内网网站，需要使用cms进行页面替换和出入境历史数据对接。到时候会派一个用过CMS的外包过去。到时候你领着他干活。<br><\/p>","type":"1","mailPassword":null,"readPerson":"王飞越(2019-11-28 09:28:16)"},{"searchValue":null,"createBy":null,"createTime":"2019-12-04 13:52:01","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"751239f964394af1b30d2de836214ee2","sendMail":"hwl@zhhl.com","sendMailPerson":"黄文龙","reMail":"17600194545@zhhl.com","reMailPerson":"管理员","ccMail":null,"ccMailPerson":null,"bccMail":null,"bccMailPerson":null,"title":"123141","attachment":null,"mailContent":"<p>42131321<\/p>","type":"1","mailPassword":null,"readPerson":"管理员"}]
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
         * createTime : 2019-11-28 09:28:16
         * updateBy : null
         * updateTime : null
         * remark : null
         * params : {}
         * id : 939e2af9294b4406b2e96ff2f0f5e7fa
         * sendMail : hwl@zhhl.com
         * sendMailPerson : 黄文龙
         * reMail : wfy@zhhl.com
         * reMailPerson : 王飞越
         * ccMail : null
         * ccMailPerson : null
         * bccMail : null
         * bccMailPerson : null
         * title : 出入境内外网站
         * attachment : b1370948c47446d8ac0faba5d4b241be
         * mailContent : <p>出入境内网网站，需要使用cms进行页面替换和出入境历史数据对接。到时候会派一个用过CMS的外包过去。到时候你领着他干活。<br></p>
         * type : 1
         * mailPassword : null
         * readPerson : 王飞越(2019-11-28 09:28:16)
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
        private Object ccMail;
        private Object ccMailPerson;
        private Object bccMail;
        private Object bccMailPerson;
        private String title;
        private String attachment;
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

        public Object getCcMail() {
            return ccMail;
        }

        public void setCcMail(Object ccMail) {
            this.ccMail = ccMail;
        }

        public Object getCcMailPerson() {
            return ccMailPerson;
        }

        public void setCcMailPerson(Object ccMailPerson) {
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

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
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
