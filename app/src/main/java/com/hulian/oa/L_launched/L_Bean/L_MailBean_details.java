package com.hulian.oa.L_launched.L_Bean;

import java.util.List;

/**
 * Created by qgl on 2020/1/2 16:14.
 */
public class L_MailBean_details {

    /**
     * msg : 操作成功
     * code : 0
     * data : {"mailDraft":{"searchValue":null,"createBy":null,"createTime":"2020-01-02 15:51:29","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"80c7d26ab27a464bb4469d1700f31719","sendMail":"hwl@zhhl.com","sendMailPerson":"黄文龙","reMail":"wxg@zhhl.com","reMailPerson":"王晓光","ccMail":"zmj@zhhl.com","ccMailPerson":"张明娟","bccMail":null,"bccMailPerson":null,"title":"喔喔喔","attachment":"[{fileName=新建文本文档.txt, filePath=D:/profile/upload/2020/01/02/ebdf642421dcbc705b3d7f717a1252d0.txt, fileId=9d88b2ff278e4e00b4e9630f57a43331}]","mailContent":"得","type":"1","mailPassword":null,"readPerson":"王晓光"},"fileList":[{"fileName":"新建文本文档.txt","filePath":"D:/profile/upload/2020/01/02/ebdf642421dcbc705b3d7f717a1252d0.txt","fileId":"9d88b2ff278e4e00b4e9630f57a43331"}]}
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
         * mailDraft : {"searchValue":null,"createBy":null,"createTime":"2020-01-02 15:51:29","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"80c7d26ab27a464bb4469d1700f31719","sendMail":"hwl@zhhl.com","sendMailPerson":"黄文龙","reMail":"wxg@zhhl.com","reMailPerson":"王晓光","ccMail":"zmj@zhhl.com","ccMailPerson":"张明娟","bccMail":null,"bccMailPerson":null,"title":"喔喔喔","attachment":"[{fileName=新建文本文档.txt, filePath=D:/profile/upload/2020/01/02/ebdf642421dcbc705b3d7f717a1252d0.txt, fileId=9d88b2ff278e4e00b4e9630f57a43331}]","mailContent":"得","type":"1","mailPassword":null,"readPerson":"王晓光"}
         * fileList : [{"fileName":"新建文本文档.txt","filePath":"D:/profile/upload/2020/01/02/ebdf642421dcbc705b3d7f717a1252d0.txt","fileId":"9d88b2ff278e4e00b4e9630f57a43331"}]
         */

        private MailDraftBean mailDraft;
        private List<FileListBean> fileList;

        public MailDraftBean getMailDraft() {
            return mailDraft;
        }

        public void setMailDraft(MailDraftBean mailDraft) {
            this.mailDraft = mailDraft;
        }

        public List<FileListBean> getFileList() {
            return fileList;
        }

        public void setFileList(List<FileListBean> fileList) {
            this.fileList = fileList;
        }

        public static class MailDraftBean {
            /**
             * searchValue : null
             * createBy : null
             * createTime : 2020-01-02 15:51:29
             * updateBy : null
             * updateTime : null
             * remark : null
             * params : {}
             * id : 80c7d26ab27a464bb4469d1700f31719
             * sendMail : hwl@zhhl.com
             * sendMailPerson : 黄文龙
             * reMail : wxg@zhhl.com
             * reMailPerson : 王晓光
             * ccMail : zmj@zhhl.com
             * ccMailPerson : 张明娟
             * bccMail : null
             * bccMailPerson : null
             * title : 喔喔喔
             * attachment : [{fileName=新建文本文档.txt, filePath=D:/profile/upload/2020/01/02/ebdf642421dcbc705b3d7f717a1252d0.txt, fileId=9d88b2ff278e4e00b4e9630f57a43331}]
             * mailContent : 得
             * type : 1
             * mailPassword : null
             * readPerson : 王晓光
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

        public static class FileListBean {
            /**
             * fileName : 新建文本文档.txt
             * filePath : D:/profile/upload/2020/01/02/ebdf642421dcbc705b3d7f717a1252d0.txt
             * fileId : 9d88b2ff278e4e00b4e9630f57a43331
             */

            private String fileName;
            private String filePath;
            private String fileId;

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

            public String getFilePath() {
                return filePath;
            }

            public void setFilePath(String filePath) {
                this.filePath = filePath;
            }

            public String getFileId() {
                return fileId;
            }

            public void setFileId(String fileId) {
                this.fileId = fileId;
            }
        }
    }
}
