package com.hulian.oa.iac.bean;

import java.util.List;

public class BacklogBean {


    /**
     * obj : {"res":{"D_BEGIN_DATE":"2017-09-14","UPDATETIME":{"date":2,"hours":17,"seconds":30,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":15,"time":1577956530000,"day":4},"V_DBR":"代办人姓名","V_LEGAL_PERSON":"法定代表人","V_MANAGER":"管理者代表","V_FR_SFZ":"220203196510290334","N_L_ID":"af3a16d5d2f041859be47db8476800d8","V_COMPAHY_PHONE":"043251325145","D_CREATE_DATE":"2017-09-14","V_CONPANY_TYPE":"1","V_URL":"www.baidu.com","FIXED_ASSETS":"5000","V_QUALITY_PERSON":"质量保证负责人","V_PERSON":"张凤阁","V_APPROVE":"220107","V_BUSINESS_APPROVE":"营业执照（事业单位法人）登记机构","V_DBR_SFZ":"220281199202040539","V_C_NAME":"长春吉大正元信息安全技术有限公司","REGISTERED_CAPITAL":"3000","V_ADDRESS":"吉林省长春市高新技术产业开发区博才路399号栖乐荟写字楼10号楼第17层","N_C_ID":"cd4d7253f77f418c96ff65b6c5b4b22c","V_POSTAL_CODE":"132000","CREATETIME":{"date":2,"hours":17,"seconds":30,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":15,"time":1577956530000,"day":4},"V_CREDIT_CODE":"91220101MA14DLR10L","V_TRADE":"所属行业","V_MAKE_ADRESS":"制造地址","V_DBR_TEL":"15584481220","V_FR_TEL":"13500889232","V_PERSON_PHONE":"13500889232","V_ZT":"0","V_MANAGER_JOB":"管理者代表职位","V_FACSIMILE":"111111","V_EMAIL":"1409421@qq.com","V_PERSON_SUM":"200"},"res1":[{"V_PRODUCTION":"代表产品（限制范围、典型产品）","UPDATETIME":{"date":6,"hours":10,"seconds":55,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":8,"time":1578276535000,"day":1},"N_L_ID":"af3a16d5d2f041859be47db8476800d8","N_LT_ID":"e12e993d19e84cdab878067d14954ce3","N_SECOND_ID":"锅炉","N_THIRD_ID":"锅炉B","V_ZT":"0","CREATETIME":{"date":2,"hours":17,"seconds":28,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":17,"time":1577956648000,"day":4},"V_PARA_ID":"额定出口压力小于等于2.5MPa的蒸汽和热水锅炉；有机热载体锅炉","N_FIRST_ID":"锅炉制造","V_UNIT":"形式试验单位"}],"N_L_ID":"af3a16d5d2f041859be47db8476800d8","xklx":"1","N_B_ID":"cd4d7253f77f418c96ff65b6c5b4b22c"}
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
         * res : {"D_BEGIN_DATE":"2017-09-14","UPDATETIME":{"date":2,"hours":17,"seconds":30,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":15,"time":1577956530000,"day":4},"V_DBR":"代办人姓名","V_LEGAL_PERSON":"法定代表人","V_MANAGER":"管理者代表","V_FR_SFZ":"220203196510290334","N_L_ID":"af3a16d5d2f041859be47db8476800d8","V_COMPAHY_PHONE":"043251325145","D_CREATE_DATE":"2017-09-14","V_CONPANY_TYPE":"1","V_URL":"www.baidu.com","FIXED_ASSETS":"5000","V_QUALITY_PERSON":"质量保证负责人","V_PERSON":"张凤阁","V_APPROVE":"220107","V_BUSINESS_APPROVE":"营业执照（事业单位法人）登记机构","V_DBR_SFZ":"220281199202040539","V_C_NAME":"长春吉大正元信息安全技术有限公司","REGISTERED_CAPITAL":"3000","V_ADDRESS":"吉林省长春市高新技术产业开发区博才路399号栖乐荟写字楼10号楼第17层","N_C_ID":"cd4d7253f77f418c96ff65b6c5b4b22c","V_POSTAL_CODE":"132000","CREATETIME":{"date":2,"hours":17,"seconds":30,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":15,"time":1577956530000,"day":4},"V_CREDIT_CODE":"91220101MA14DLR10L","V_TRADE":"所属行业","V_MAKE_ADRESS":"制造地址","V_DBR_TEL":"15584481220","V_FR_TEL":"13500889232","V_PERSON_PHONE":"13500889232","V_ZT":"0","V_MANAGER_JOB":"管理者代表职位","V_FACSIMILE":"111111","V_EMAIL":"1409421@qq.com","V_PERSON_SUM":"200"}
         * res1 : [{"V_PRODUCTION":"代表产品（限制范围、典型产品）","UPDATETIME":{"date":6,"hours":10,"seconds":55,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":8,"time":1578276535000,"day":1},"N_L_ID":"af3a16d5d2f041859be47db8476800d8","N_LT_ID":"e12e993d19e84cdab878067d14954ce3","N_SECOND_ID":"锅炉","N_THIRD_ID":"锅炉B","V_ZT":"0","CREATETIME":{"date":2,"hours":17,"seconds":28,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":17,"time":1577956648000,"day":4},"V_PARA_ID":"额定出口压力小于等于2.5MPa的蒸汽和热水锅炉；有机热载体锅炉","N_FIRST_ID":"锅炉制造","V_UNIT":"形式试验单位"}]
         * N_L_ID : af3a16d5d2f041859be47db8476800d8
         * xklx : 1
         * N_B_ID : cd4d7253f77f418c96ff65b6c5b4b22c
         */

        private ResBean res;
        private String N_L_ID;
        private String xklx;
        private String N_B_ID;
        private List<Res1Bean> res1;

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

        public List<Res1Bean> getRes1() {
            return res1;
        }

        public void setRes1(List<Res1Bean> res1) {
            this.res1 = res1;
        }

        public static class ResBean {
            /**
             * D_BEGIN_DATE : 2017-09-14
             * UPDATETIME : {"date":2,"hours":17,"seconds":30,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":15,"time":1577956530000,"day":4}
             * V_DBR : 代办人姓名
             * V_LEGAL_PERSON : 法定代表人
             * V_MANAGER : 管理者代表
             * V_FR_SFZ : 220203196510290334
             * N_L_ID : af3a16d5d2f041859be47db8476800d8
             * V_COMPAHY_PHONE : 043251325145
             * D_CREATE_DATE : 2017-09-14
             * V_CONPANY_TYPE : 1
             * V_URL : www.baidu.com
             * FIXED_ASSETS : 5000
             * V_QUALITY_PERSON : 质量保证负责人
             * V_PERSON : 张凤阁
             * V_APPROVE : 220107
             * V_BUSINESS_APPROVE : 营业执照（事业单位法人）登记机构
             * V_DBR_SFZ : 220281199202040539
             * V_C_NAME : 长春吉大正元信息安全技术有限公司
             * REGISTERED_CAPITAL : 3000
             * V_ADDRESS : 吉林省长春市高新技术产业开发区博才路399号栖乐荟写字楼10号楼第17层
             * N_C_ID : cd4d7253f77f418c96ff65b6c5b4b22c
             * V_POSTAL_CODE : 132000
             * CREATETIME : {"date":2,"hours":17,"seconds":30,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":15,"time":1577956530000,"day":4}
             * V_CREDIT_CODE : 91220101MA14DLR10L
             * V_TRADE : 所属行业
             * V_MAKE_ADRESS : 制造地址
             * V_DBR_TEL : 15584481220
             * V_FR_TEL : 13500889232
             * V_PERSON_PHONE : 13500889232
             * V_ZT : 0
             * V_MANAGER_JOB : 管理者代表职位
             * V_FACSIMILE : 111111
             * V_EMAIL : 1409421@qq.com
             * V_PERSON_SUM : 200
             */

            private String D_BEGIN_DATE;
            private UPDATETIMEBean UPDATETIME;
            private String V_DBR;
            private String V_LEGAL_PERSON;
            private String V_MANAGER;
            private String V_FR_SFZ;
            private String N_L_ID;
            private String V_COMPAHY_PHONE;
            private String D_CREATE_DATE;
            private String V_CONPANY_TYPE;
            private String V_URL;
            private String FIXED_ASSETS;
            private String V_QUALITY_PERSON;
            private String V_PERSON;
            private String V_APPROVE;
            private String V_BUSINESS_APPROVE;
            private String V_DBR_SFZ;
            private String V_C_NAME;
            private String REGISTERED_CAPITAL;
            private String V_ADDRESS;
            private String N_C_ID;
            private String V_POSTAL_CODE;
            private CREATETIMEBean CREATETIME;
            private String V_CREDIT_CODE;
            private String V_TRADE;
            private String V_MAKE_ADRESS;
            private String V_DBR_TEL;
            private String V_FR_TEL;
            private String V_PERSON_PHONE;
            private String V_ZT;
            private String V_MANAGER_JOB;
            private String V_FACSIMILE;
            private String V_EMAIL;
            private String V_PERSON_SUM;

            public String getD_BEGIN_DATE() {
                return D_BEGIN_DATE;
            }

            public void setD_BEGIN_DATE(String D_BEGIN_DATE) {
                this.D_BEGIN_DATE = D_BEGIN_DATE;
            }

            public UPDATETIMEBean getUPDATETIME() {
                return UPDATETIME;
            }

            public void setUPDATETIME(UPDATETIMEBean UPDATETIME) {
                this.UPDATETIME = UPDATETIME;
            }

            public String getV_DBR() {
                return V_DBR;
            }

            public void setV_DBR(String V_DBR) {
                this.V_DBR = V_DBR;
            }

            public String getV_LEGAL_PERSON() {
                return V_LEGAL_PERSON;
            }

            public void setV_LEGAL_PERSON(String V_LEGAL_PERSON) {
                this.V_LEGAL_PERSON = V_LEGAL_PERSON;
            }

            public String getV_MANAGER() {
                return V_MANAGER;
            }

            public void setV_MANAGER(String V_MANAGER) {
                this.V_MANAGER = V_MANAGER;
            }

            public String getV_FR_SFZ() {
                return V_FR_SFZ;
            }

            public void setV_FR_SFZ(String V_FR_SFZ) {
                this.V_FR_SFZ = V_FR_SFZ;
            }

            public String getN_L_ID() {
                return N_L_ID;
            }

            public void setN_L_ID(String N_L_ID) {
                this.N_L_ID = N_L_ID;
            }

            public String getV_COMPAHY_PHONE() {
                return V_COMPAHY_PHONE;
            }

            public void setV_COMPAHY_PHONE(String V_COMPAHY_PHONE) {
                this.V_COMPAHY_PHONE = V_COMPAHY_PHONE;
            }

            public String getD_CREATE_DATE() {
                return D_CREATE_DATE;
            }

            public void setD_CREATE_DATE(String D_CREATE_DATE) {
                this.D_CREATE_DATE = D_CREATE_DATE;
            }

            public String getV_CONPANY_TYPE() {
                return V_CONPANY_TYPE;
            }

            public void setV_CONPANY_TYPE(String V_CONPANY_TYPE) {
                this.V_CONPANY_TYPE = V_CONPANY_TYPE;
            }

            public String getV_URL() {
                return V_URL;
            }

            public void setV_URL(String V_URL) {
                this.V_URL = V_URL;
            }

            public String getFIXED_ASSETS() {
                return FIXED_ASSETS;
            }

            public void setFIXED_ASSETS(String FIXED_ASSETS) {
                this.FIXED_ASSETS = FIXED_ASSETS;
            }

            public String getV_QUALITY_PERSON() {
                return V_QUALITY_PERSON;
            }

            public void setV_QUALITY_PERSON(String V_QUALITY_PERSON) {
                this.V_QUALITY_PERSON = V_QUALITY_PERSON;
            }

            public String getV_PERSON() {
                return V_PERSON;
            }

            public void setV_PERSON(String V_PERSON) {
                this.V_PERSON = V_PERSON;
            }

            public String getV_APPROVE() {
                return V_APPROVE;
            }

            public void setV_APPROVE(String V_APPROVE) {
                this.V_APPROVE = V_APPROVE;
            }

            public String getV_BUSINESS_APPROVE() {
                return V_BUSINESS_APPROVE;
            }

            public void setV_BUSINESS_APPROVE(String V_BUSINESS_APPROVE) {
                this.V_BUSINESS_APPROVE = V_BUSINESS_APPROVE;
            }

            public String getV_DBR_SFZ() {
                return V_DBR_SFZ;
            }

            public void setV_DBR_SFZ(String V_DBR_SFZ) {
                this.V_DBR_SFZ = V_DBR_SFZ;
            }

            public String getV_C_NAME() {
                return V_C_NAME;
            }

            public void setV_C_NAME(String V_C_NAME) {
                this.V_C_NAME = V_C_NAME;
            }

            public String getREGISTERED_CAPITAL() {
                return REGISTERED_CAPITAL;
            }

            public void setREGISTERED_CAPITAL(String REGISTERED_CAPITAL) {
                this.REGISTERED_CAPITAL = REGISTERED_CAPITAL;
            }

            public String getV_ADDRESS() {
                return V_ADDRESS;
            }

            public void setV_ADDRESS(String V_ADDRESS) {
                this.V_ADDRESS = V_ADDRESS;
            }

            public String getN_C_ID() {
                return N_C_ID;
            }

            public void setN_C_ID(String N_C_ID) {
                this.N_C_ID = N_C_ID;
            }

            public String getV_POSTAL_CODE() {
                return V_POSTAL_CODE;
            }

            public void setV_POSTAL_CODE(String V_POSTAL_CODE) {
                this.V_POSTAL_CODE = V_POSTAL_CODE;
            }

            public CREATETIMEBean getCREATETIME() {
                return CREATETIME;
            }

            public void setCREATETIME(CREATETIMEBean CREATETIME) {
                this.CREATETIME = CREATETIME;
            }

            public String getV_CREDIT_CODE() {
                return V_CREDIT_CODE;
            }

            public void setV_CREDIT_CODE(String V_CREDIT_CODE) {
                this.V_CREDIT_CODE = V_CREDIT_CODE;
            }

            public String getV_TRADE() {
                return V_TRADE;
            }

            public void setV_TRADE(String V_TRADE) {
                this.V_TRADE = V_TRADE;
            }

            public String getV_MAKE_ADRESS() {
                return V_MAKE_ADRESS;
            }

            public void setV_MAKE_ADRESS(String V_MAKE_ADRESS) {
                this.V_MAKE_ADRESS = V_MAKE_ADRESS;
            }

            public String getV_DBR_TEL() {
                return V_DBR_TEL;
            }

            public void setV_DBR_TEL(String V_DBR_TEL) {
                this.V_DBR_TEL = V_DBR_TEL;
            }

            public String getV_FR_TEL() {
                return V_FR_TEL;
            }

            public void setV_FR_TEL(String V_FR_TEL) {
                this.V_FR_TEL = V_FR_TEL;
            }

            public String getV_PERSON_PHONE() {
                return V_PERSON_PHONE;
            }

            public void setV_PERSON_PHONE(String V_PERSON_PHONE) {
                this.V_PERSON_PHONE = V_PERSON_PHONE;
            }

            public String getV_ZT() {
                return V_ZT;
            }

            public void setV_ZT(String V_ZT) {
                this.V_ZT = V_ZT;
            }

            public String getV_MANAGER_JOB() {
                return V_MANAGER_JOB;
            }

            public void setV_MANAGER_JOB(String V_MANAGER_JOB) {
                this.V_MANAGER_JOB = V_MANAGER_JOB;
            }

            public String getV_FACSIMILE() {
                return V_FACSIMILE;
            }

            public void setV_FACSIMILE(String V_FACSIMILE) {
                this.V_FACSIMILE = V_FACSIMILE;
            }

            public String getV_EMAIL() {
                return V_EMAIL;
            }

            public void setV_EMAIL(String V_EMAIL) {
                this.V_EMAIL = V_EMAIL;
            }

            public String getV_PERSON_SUM() {
                return V_PERSON_SUM;
            }

            public void setV_PERSON_SUM(String V_PERSON_SUM) {
                this.V_PERSON_SUM = V_PERSON_SUM;
            }

            public static class UPDATETIMEBean {
                /**
                 * date : 2
                 * hours : 17
                 * seconds : 30
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 15
                 * time : 1577956530000
                 * day : 4
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
                 * date : 2
                 * hours : 17
                 * seconds : 30
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 15
                 * time : 1577956530000
                 * day : 4
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

        public static class Res1Bean {
            /**
             * V_PRODUCTION : 代表产品（限制范围、典型产品）
             * UPDATETIME : {"date":6,"hours":10,"seconds":55,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":8,"time":1578276535000,"day":1}
             * N_L_ID : af3a16d5d2f041859be47db8476800d8
             * N_LT_ID : e12e993d19e84cdab878067d14954ce3
             * N_SECOND_ID : 锅炉
             * N_THIRD_ID : 锅炉B
             * V_ZT : 0
             * CREATETIME : {"date":2,"hours":17,"seconds":28,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":17,"time":1577956648000,"day":4}
             * V_PARA_ID : 额定出口压力小于等于2.5MPa的蒸汽和热水锅炉；有机热载体锅炉
             * N_FIRST_ID : 锅炉制造
             * V_UNIT : 形式试验单位
             */

            private String V_PRODUCTION;
            private UPDATETIMEBeanX UPDATETIME;
            private String N_L_ID;
            private String N_LT_ID;
            private String N_SECOND_ID;
            private String N_THIRD_ID;
            private String V_ZT;
            private CREATETIMEBeanX CREATETIME;
            private String V_PARA_ID;
            private String N_FIRST_ID;
            private String V_UNIT;

            public String getV_PRODUCTION() {
                return V_PRODUCTION;
            }

            public void setV_PRODUCTION(String V_PRODUCTION) {
                this.V_PRODUCTION = V_PRODUCTION;
            }

            public UPDATETIMEBeanX getUPDATETIME() {
                return UPDATETIME;
            }

            public void setUPDATETIME(UPDATETIMEBeanX UPDATETIME) {
                this.UPDATETIME = UPDATETIME;
            }

            public String getN_L_ID() {
                return N_L_ID;
            }

            public void setN_L_ID(String N_L_ID) {
                this.N_L_ID = N_L_ID;
            }

            public String getN_LT_ID() {
                return N_LT_ID;
            }

            public void setN_LT_ID(String N_LT_ID) {
                this.N_LT_ID = N_LT_ID;
            }

            public String getN_SECOND_ID() {
                return N_SECOND_ID;
            }

            public void setN_SECOND_ID(String N_SECOND_ID) {
                this.N_SECOND_ID = N_SECOND_ID;
            }

            public String getN_THIRD_ID() {
                return N_THIRD_ID;
            }

            public void setN_THIRD_ID(String N_THIRD_ID) {
                this.N_THIRD_ID = N_THIRD_ID;
            }

            public String getV_ZT() {
                return V_ZT;
            }

            public void setV_ZT(String V_ZT) {
                this.V_ZT = V_ZT;
            }

            public CREATETIMEBeanX getCREATETIME() {
                return CREATETIME;
            }

            public void setCREATETIME(CREATETIMEBeanX CREATETIME) {
                this.CREATETIME = CREATETIME;
            }

            public String getV_PARA_ID() {
                return V_PARA_ID;
            }

            public void setV_PARA_ID(String V_PARA_ID) {
                this.V_PARA_ID = V_PARA_ID;
            }

            public String getN_FIRST_ID() {
                return N_FIRST_ID;
            }

            public void setN_FIRST_ID(String N_FIRST_ID) {
                this.N_FIRST_ID = N_FIRST_ID;
            }

            public String getV_UNIT() {
                return V_UNIT;
            }

            public void setV_UNIT(String V_UNIT) {
                this.V_UNIT = V_UNIT;
            }

            public static class UPDATETIMEBeanX {
                /**
                 * date : 6
                 * hours : 10
                 * seconds : 55
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 8
                 * time : 1578276535000
                 * day : 1
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

            public static class CREATETIMEBeanX {
                /**
                 * date : 2
                 * hours : 17
                 * seconds : 28
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 17
                 * time : 1577956648000
                 * day : 4
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
