package com.hulian.oa.iac.bean;

import java.util.List;

public class DepartmentsBean {


    /**
     * obj : {"res":[{"V_D_DEPARTMENT":"1","PSYJ":"1","N_D_ID":"4454a18b927546078062954829c87d27","UPDATETIME":{"date":22,"hours":9,"seconds":37,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656097000,"day":3},"N_L_ID":"241f7ef842174562ba7eb947ccb43c24","PSZT":"0","V_ZT":"0","N_C_ID":"e0fe870f52eb4d918efa196ef7df0b85","V_D_NUM":"2","CREATETIME":{"date":22,"hours":9,"seconds":37,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656097000,"day":3},"V_D_PERSON":"1","PSSJ":"2020-01-22"}],"N_L_ID":"241f7ef842174562ba7eb947ccb43c24","xklx":"1","N_B_ID":"1ca7284011e64bb988036c05ba0bd5f3"}
     * code : 200
     * msg : 成功
     */

    private ObjBean obj;
    private String code;
    private String msg;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ObjBean {
        /**
         * res : [{"V_D_DEPARTMENT":"1","PSYJ":"1","N_D_ID":"4454a18b927546078062954829c87d27","UPDATETIME":{"date":22,"hours":9,"seconds":37,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656097000,"day":3},"N_L_ID":"241f7ef842174562ba7eb947ccb43c24","PSZT":"0","V_ZT":"0","N_C_ID":"e0fe870f52eb4d918efa196ef7df0b85","V_D_NUM":"2","CREATETIME":{"date":22,"hours":9,"seconds":37,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656097000,"day":3},"V_D_PERSON":"1","PSSJ":"2020-01-22"}]
         * N_L_ID : 241f7ef842174562ba7eb947ccb43c24
         * xklx : 1
         * N_B_ID : 1ca7284011e64bb988036c05ba0bd5f3
         */

        private String N_L_ID;
        private String xklx;
        private String N_B_ID;
        private List<ResBean> res;

        public String getN_L_ID() {
            return N_L_ID;
        }

        public void setN_L_ID(String N_L_ID) {
            this.N_L_ID = N_L_ID;
        }

        public String getXklx() {
            return xklx;
        }

        public void setXklx(String xklx) {
            this.xklx = xklx;
        }

        public String getN_B_ID() {
            return N_B_ID;
        }

        public void setN_B_ID(String N_B_ID) {
            this.N_B_ID = N_B_ID;
        }

        public List<ResBean> getRes() {
            return res;
        }

        public void setRes(List<ResBean> res) {
            this.res = res;
        }

        public static class ResBean {
            /**
             * V_D_DEPARTMENT : 1
             * PSYJ : 1
             * N_D_ID : 4454a18b927546078062954829c87d27
             * UPDATETIME : {"date":22,"hours":9,"seconds":37,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656097000,"day":3}
             * N_L_ID : 241f7ef842174562ba7eb947ccb43c24
             * PSZT : 0
             * V_ZT : 0
             * N_C_ID : e0fe870f52eb4d918efa196ef7df0b85
             * V_D_NUM : 2
             * CREATETIME : {"date":22,"hours":9,"seconds":37,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656097000,"day":3}
             * V_D_PERSON : 1
             * PSSJ : 2020-01-22
             */

            private String V_D_DEPARTMENT;
            private String PSYJ;
            private String N_D_ID;
            private UPDATETIMEBean UPDATETIME;
            private String N_L_ID;
            private String PSZT;
            private String V_ZT;
            private String N_C_ID;
            private String V_D_NUM;
            private CREATETIMEBean CREATETIME;
            private String V_D_PERSON;
            private String PSSJ;

            public String getV_D_DEPARTMENT() {
                return V_D_DEPARTMENT;
            }

            public void setV_D_DEPARTMENT(String V_D_DEPARTMENT) {
                this.V_D_DEPARTMENT = V_D_DEPARTMENT;
            }

            public String getPSYJ() {
                return PSYJ;
            }

            public void setPSYJ(String PSYJ) {
                this.PSYJ = PSYJ;
            }

            public String getN_D_ID() {
                return N_D_ID;
            }

            public void setN_D_ID(String N_D_ID) {
                this.N_D_ID = N_D_ID;
            }

            public UPDATETIMEBean getUPDATETIME() {
                return UPDATETIME;
            }

            public void setUPDATETIME(UPDATETIMEBean UPDATETIME) {
                this.UPDATETIME = UPDATETIME;
            }

            public String getN_L_ID() {
                return N_L_ID;
            }

            public void setN_L_ID(String N_L_ID) {
                this.N_L_ID = N_L_ID;
            }

            public String getPSZT() {
                return PSZT;
            }

            public void setPSZT(String PSZT) {
                this.PSZT = PSZT;
            }

            public String getV_ZT() {
                return V_ZT;
            }

            public void setV_ZT(String V_ZT) {
                this.V_ZT = V_ZT;
            }

            public String getN_C_ID() {
                return N_C_ID;
            }

            public void setN_C_ID(String N_C_ID) {
                this.N_C_ID = N_C_ID;
            }

            public String getV_D_NUM() {
                return V_D_NUM;
            }

            public void setV_D_NUM(String V_D_NUM) {
                this.V_D_NUM = V_D_NUM;
            }

            public CREATETIMEBean getCREATETIME() {
                return CREATETIME;
            }

            public void setCREATETIME(CREATETIMEBean CREATETIME) {
                this.CREATETIME = CREATETIME;
            }

            public String getV_D_PERSON() {
                return V_D_PERSON;
            }

            public void setV_D_PERSON(String V_D_PERSON) {
                this.V_D_PERSON = V_D_PERSON;
            }

            public String getPSSJ() {
                return PSSJ;
            }

            public void setPSSJ(String PSSJ) {
                this.PSSJ = PSSJ;
            }

            public static class UPDATETIMEBean {
                /**
                 * date : 22
                 * hours : 9
                 * seconds : 37
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 21
                 * time : 1579656097000
                 * day : 3
                 */

                private int date;
                private int hours;
                private int seconds;
                private int month;
                private int nanos;
                private int timezoneOffset;
                private int year;
                private int minutes;
                private long time;
                private int day;

                public int getDate() {
                    return date;
                }

                public void setDate(int date) {
                    this.date = date;
                }

                public int getHours() {
                    return hours;
                }

                public void setHours(int hours) {
                    this.hours = hours;
                }

                public int getSeconds() {
                    return seconds;
                }

                public void setSeconds(int seconds) {
                    this.seconds = seconds;
                }

                public int getMonth() {
                    return month;
                }

                public void setMonth(int month) {
                    this.month = month;
                }

                public int getNanos() {
                    return nanos;
                }

                public void setNanos(int nanos) {
                    this.nanos = nanos;
                }

                public int getTimezoneOffset() {
                    return timezoneOffset;
                }

                public void setTimezoneOffset(int timezoneOffset) {
                    this.timezoneOffset = timezoneOffset;
                }

                public int getYear() {
                    return year;
                }

                public void setYear(int year) {
                    this.year = year;
                }

                public int getMinutes() {
                    return minutes;
                }

                public void setMinutes(int minutes) {
                    this.minutes = minutes;
                }

                public long getTime() {
                    return time;
                }

                public void setTime(long time) {
                    this.time = time;
                }

                public int getDay() {
                    return day;
                }

                public void setDay(int day) {
                    this.day = day;
                }
            }

            public static class CREATETIMEBean {
                /**
                 * date : 22
                 * hours : 9
                 * seconds : 37
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 21
                 * time : 1579656097000
                 * day : 3
                 */

                private int date;
                private int hours;
                private int seconds;
                private int month;
                private int nanos;
                private int timezoneOffset;
                private int year;
                private int minutes;
                private long time;
                private int day;

                public int getDate() {
                    return date;
                }

                public void setDate(int date) {
                    this.date = date;
                }

                public int getHours() {
                    return hours;
                }

                public void setHours(int hours) {
                    this.hours = hours;
                }

                public int getSeconds() {
                    return seconds;
                }

                public void setSeconds(int seconds) {
                    this.seconds = seconds;
                }

                public int getMonth() {
                    return month;
                }

                public void setMonth(int month) {
                    this.month = month;
                }

                public int getNanos() {
                    return nanos;
                }

                public void setNanos(int nanos) {
                    this.nanos = nanos;
                }

                public int getTimezoneOffset() {
                    return timezoneOffset;
                }

                public void setTimezoneOffset(int timezoneOffset) {
                    this.timezoneOffset = timezoneOffset;
                }

                public int getYear() {
                    return year;
                }

                public void setYear(int year) {
                    this.year = year;
                }

                public int getMinutes() {
                    return minutes;
                }

                public void setMinutes(int minutes) {
                    this.minutes = minutes;
                }

                public long getTime() {
                    return time;
                }

                public void setTime(long time) {
                    this.time = time;
                }

                public int getDay() {
                    return day;
                }

                public void setDay(int day) {
                    this.day = day;
                }
            }
        }
    }
}
