package com.hulian.oa.bean;

import java.util.List;

/**
 * Created by qgl on 2020/3/27 9:01.
 */
public class ClueBean {

    /**
     * msg : 操作成功
     * code : 0
     * data : [{"YMD":"2020-03-27","STATE":"N"}]
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
         * YMD : 2020-03-27
         * STATE : N
         */

        private String YMD;
        private String STATE;

        public String getYMD() {
            return YMD;
        }

        public void setYMD(String YMD) {
            this.YMD = YMD;
        }

        public String getSTATE() {
            return STATE;
        }

        public void setSTATE(String STATE) {
            this.STATE = STATE;
        }
    }
}
