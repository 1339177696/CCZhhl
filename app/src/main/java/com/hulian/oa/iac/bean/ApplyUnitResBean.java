package com.hulian.oa.iac.bean;

public class ApplyUnitResBean {


    /**
     * obj : {"res":{"V_SOLDERING_NUM":"1","N_R_ID":"f0c1a0cc2dd94ccdaaa8eb2e7bdf61c9","UPDATETIME":{"date":22,"hours":9,"seconds":31,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656091000,"day":3},"V_QUALITY_NUM":"1","V_AREA_OFFICE":"1","N_L_ID":"241f7ef842174562ba7eb947ccb43c24","V_AREA_PLANT":"1","V_AREA_WAREHOUSE":"1","V_OTHER_NUM":"1","V_ZT":"0","V_BREAKDOWN_NUM":"1","V_TECHNOLOGY_NUM":"1","PSYJ":"1","V_STAFF_SUM":"111","V_OUTPUT_VALUE":"14","PSZT":"0","V_AREA_SUM":"11","N_C_ID":"e0fe870f52eb4d918efa196ef7df0b85","CREATETIME":{"date":22,"hours":9,"seconds":31,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656091000,"day":3},"V_WORK_NUM":"1","PSSJ":"2020-01-22"},"N_L_ID":"241f7ef842174562ba7eb947ccb43c24","xklx":"1","N_B_ID":"1ca7284011e64bb988036c05ba0bd5f3"}
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
         * res : {"V_SOLDERING_NUM":"1","N_R_ID":"f0c1a0cc2dd94ccdaaa8eb2e7bdf61c9","UPDATETIME":{"date":22,"hours":9,"seconds":31,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656091000,"day":3},"V_QUALITY_NUM":"1","V_AREA_OFFICE":"1","N_L_ID":"241f7ef842174562ba7eb947ccb43c24","V_AREA_PLANT":"1","V_AREA_WAREHOUSE":"1","V_OTHER_NUM":"1","V_ZT":"0","V_BREAKDOWN_NUM":"1","V_TECHNOLOGY_NUM":"1","PSYJ":"1","V_STAFF_SUM":"111","V_OUTPUT_VALUE":"14","PSZT":"0","V_AREA_SUM":"11","N_C_ID":"e0fe870f52eb4d918efa196ef7df0b85","CREATETIME":{"date":22,"hours":9,"seconds":31,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656091000,"day":3},"V_WORK_NUM":"1","PSSJ":"2020-01-22"}
         * N_L_ID : 241f7ef842174562ba7eb947ccb43c24
         * xklx : 1
         * N_B_ID : 1ca7284011e64bb988036c05ba0bd5f3
         */

        private ResBean res;
        private String N_L_ID;
        private String xklx;
        private String N_B_ID;

        public ResBean getRes() {
            return res;
        }

        public void setRes(ResBean res) {
            this.res = res;
        }

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

        public static class ResBean {
            /**
             * V_SOLDERING_NUM : 1
             * N_R_ID : f0c1a0cc2dd94ccdaaa8eb2e7bdf61c9
             * UPDATETIME : {"date":22,"hours":9,"seconds":31,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656091000,"day":3}
             * V_QUALITY_NUM : 1
             * V_AREA_OFFICE : 1
             * N_L_ID : 241f7ef842174562ba7eb947ccb43c24
             * V_AREA_PLANT : 1
             * V_AREA_WAREHOUSE : 1
             * V_OTHER_NUM : 1
             * V_ZT : 0
             * V_BREAKDOWN_NUM : 1
             * V_TECHNOLOGY_NUM : 1
             * PSYJ : 1
             * V_STAFF_SUM : 111
             * V_OUTPUT_VALUE : 14
             * PSZT : 0
             * V_AREA_SUM : 11
             * N_C_ID : e0fe870f52eb4d918efa196ef7df0b85
             * CREATETIME : {"date":22,"hours":9,"seconds":31,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656091000,"day":3}
             * V_WORK_NUM : 1
             * PSSJ : 2020-01-22
             */

            private String V_SOLDERING_NUM;
            private String N_R_ID;
            private UPDATETIMEBean UPDATETIME;
            private String V_QUALITY_NUM;
            private String V_AREA_OFFICE;
            private String N_L_ID;
            private String V_AREA_PLANT;
            private String V_AREA_WAREHOUSE;
            private String V_OTHER_NUM;
            private String V_ZT;
            private String V_BREAKDOWN_NUM;
            private String V_TECHNOLOGY_NUM;
            private String PSYJ;
            private String V_STAFF_SUM;
            private String V_OUTPUT_VALUE;
            private String PSZT;
            private String V_AREA_SUM;
            private String N_C_ID;
            private CREATETIMEBean CREATETIME;
            private String V_WORK_NUM;
            private String PSSJ;

            public String getV_SOLDERING_NUM() {
                return V_SOLDERING_NUM;
            }

            public void setV_SOLDERING_NUM(String V_SOLDERING_NUM) {
                this.V_SOLDERING_NUM = V_SOLDERING_NUM;
            }

            public String getN_R_ID() {
                return N_R_ID;
            }

            public void setN_R_ID(String N_R_ID) {
                this.N_R_ID = N_R_ID;
            }

            public UPDATETIMEBean getUPDATETIME() {
                return UPDATETIME;
            }

            public void setUPDATETIME(UPDATETIMEBean UPDATETIME) {
                this.UPDATETIME = UPDATETIME;
            }

            public String getV_QUALITY_NUM() {
                return V_QUALITY_NUM;
            }

            public void setV_QUALITY_NUM(String V_QUALITY_NUM) {
                this.V_QUALITY_NUM = V_QUALITY_NUM;
            }

            public String getV_AREA_OFFICE() {
                return V_AREA_OFFICE;
            }

            public void setV_AREA_OFFICE(String V_AREA_OFFICE) {
                this.V_AREA_OFFICE = V_AREA_OFFICE;
            }

            public String getN_L_ID() {
                return N_L_ID;
            }

            public void setN_L_ID(String N_L_ID) {
                this.N_L_ID = N_L_ID;
            }

            public String getV_AREA_PLANT() {
                return V_AREA_PLANT;
            }

            public void setV_AREA_PLANT(String V_AREA_PLANT) {
                this.V_AREA_PLANT = V_AREA_PLANT;
            }

            public String getV_AREA_WAREHOUSE() {
                return V_AREA_WAREHOUSE;
            }

            public void setV_AREA_WAREHOUSE(String V_AREA_WAREHOUSE) {
                this.V_AREA_WAREHOUSE = V_AREA_WAREHOUSE;
            }

            public String getV_OTHER_NUM() {
                return V_OTHER_NUM;
            }

            public void setV_OTHER_NUM(String V_OTHER_NUM) {
                this.V_OTHER_NUM = V_OTHER_NUM;
            }

            public String getV_ZT() {
                return V_ZT;
            }

            public void setV_ZT(String V_ZT) {
                this.V_ZT = V_ZT;
            }

            public String getV_BREAKDOWN_NUM() {
                return V_BREAKDOWN_NUM;
            }

            public void setV_BREAKDOWN_NUM(String V_BREAKDOWN_NUM) {
                this.V_BREAKDOWN_NUM = V_BREAKDOWN_NUM;
            }

            public String getV_TECHNOLOGY_NUM() {
                return V_TECHNOLOGY_NUM;
            }

            public void setV_TECHNOLOGY_NUM(String V_TECHNOLOGY_NUM) {
                this.V_TECHNOLOGY_NUM = V_TECHNOLOGY_NUM;
            }

            public String getPSYJ() {
                return PSYJ;
            }

            public void setPSYJ(String PSYJ) {
                this.PSYJ = PSYJ;
            }

            public String getV_STAFF_SUM() {
                return V_STAFF_SUM;
            }

            public void setV_STAFF_SUM(String V_STAFF_SUM) {
                this.V_STAFF_SUM = V_STAFF_SUM;
            }

            public String getV_OUTPUT_VALUE() {
                return V_OUTPUT_VALUE;
            }

            public void setV_OUTPUT_VALUE(String V_OUTPUT_VALUE) {
                this.V_OUTPUT_VALUE = V_OUTPUT_VALUE;
            }

            public String getPSZT() {
                return PSZT;
            }

            public void setPSZT(String PSZT) {
                this.PSZT = PSZT;
            }

            public String getV_AREA_SUM() {
                return V_AREA_SUM;
            }

            public void setV_AREA_SUM(String V_AREA_SUM) {
                this.V_AREA_SUM = V_AREA_SUM;
            }

            public String getN_C_ID() {
                return N_C_ID;
            }

            public void setN_C_ID(String N_C_ID) {
                this.N_C_ID = N_C_ID;
            }

            public CREATETIMEBean getCREATETIME() {
                return CREATETIME;
            }

            public void setCREATETIME(CREATETIMEBean CREATETIME) {
                this.CREATETIME = CREATETIME;
            }

            public String getV_WORK_NUM() {
                return V_WORK_NUM;
            }

            public void setV_WORK_NUM(String V_WORK_NUM) {
                this.V_WORK_NUM = V_WORK_NUM;
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
                 * seconds : 31
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 21
                 * time : 1579656091000
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
                 * seconds : 31
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 21
                 * time : 1579656091000
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
