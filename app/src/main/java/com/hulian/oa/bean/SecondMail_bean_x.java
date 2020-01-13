package com.hulian.oa.bean;

import java.io.Serializable;
import java.util.List;

import io.reactivex.internal.fuseable.ScalarCallable;

/**
 * Created by czy on 2019/8/6 14:39.
 */
public class SecondMail_bean_x implements Serializable
{

        /**
         * CCP : 刘欣<lx@zhhl.com>,佟璐<tl@zhhl.com>
         * isAttach : true
         * sender : 段云龙<dyl@zhhl.com>
         * sendDate : 2019-08-06 17:00:52
         * recipients : 张博<zb@zhhl.com>,丁小龙<dxl@zhhl.com>
         * attach : [{"fileName":"微信图片_20190605165554.jpg","filePath":"https://abc.cczhhl.cn/mailfile/15650856776861.jpg"},{"fileName":"微信图片_20190605165554.jpg","filePath":"https://abc.cczhhl.cn/mailfile/15650856778332.jpg"}]
         * id : <18608730.3.1565085120097.JavaMail.fengjl@fengjl-PC>
         * title : 测试标题861750
         * isSeen : false
         * content : 测试内容861750
         */

        private String CCP;
        private boolean isAttach;
        private String sender;
        private String sendDate;
        private String recipients;
        private String id;
        private String title;
        private boolean isSeen;
        private String content;

    public String getSf() {
        return sf;
    }

    public void setSf(String sf) {
        this.sf = sf;
    }

    private String sf;  // 收发件箱判断

    public String getCollectState() {
        return collectState;
    }

    public void setCollectState(String collectState) {
        this.collectState = collectState;
    }

    private String collectState;
        private List<AttachBean> attach;

        public String getCCP() {
            return CCP;
        }

        public void setCCP(String CCP) {
            this.CCP = CCP;
        }

        public boolean isIsAttach() {
            return isAttach;
        }

        public void setIsAttach(boolean isAttach) {
            this.isAttach = isAttach;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getSendDate() {
            return sendDate;
        }

        public void setSendDate(String sendDate) {
            this.sendDate = sendDate;
        }

        public String getRecipients() {
            return recipients;
        }

        public void setRecipients(String recipients) {
            this.recipients = recipients;
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

        public boolean isIsSeen() {
            return isSeen;
        }

        public void setIsSeen(boolean isSeen) {
            this.isSeen = isSeen;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<AttachBean> getAttach() {
            return attach;
        }

        public void setAttach(List<AttachBean> attach) {
            this.attach = attach;
        }

        public static class AttachBean implements Serializable {
            /**
             * fileName : 微信图片_20190605165554.jpg
             * filePath : https://abc.cczhhl.cn/mailfile/15650856776861.jpg
             */

            private String fileName;
            private String filePath;

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
        }

}
