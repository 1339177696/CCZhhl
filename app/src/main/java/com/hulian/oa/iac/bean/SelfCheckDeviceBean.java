package com.hulian.oa.iac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SelfCheckDeviceBean {


    /**
     * obj : {"res":[{"V_V_PERFOR":"2","UPDATETIME":{"date":22,"hours":9,"seconds":22,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":22,"time":1579656142000,"day":3},"N_L_ID":"241f7ef842174562ba7eb947ccb43c24","N_V_ID":"5daa9b5971fe421c81dc6ccf955318a5","V_V_NAME":"3","V_V_NUM":"1","PSYJ":"1","V_V_TYPE":"4","D_V_BEGIN":"2020-01-22","PSZT":"0","ZT":"0","N_C_ID":"e0fe870f52eb4d918efa196ef7df0b85","CREATETIME":{"date":22,"hours":9,"seconds":22,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":22,"time":1579656142000,"day":3},"PSSJ":"2020-01-22"}],"N_L_ID":"241f7ef842174562ba7eb947ccb43c24","xklx":"1","N_B_ID":"1ca7284011e64bb988036c05ba0bd5f3"}
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
         * res : [{"V_V_PERFOR":"2","UPDATETIME":{"date":22,"hours":9,"seconds":22,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":22,"time":1579656142000,"day":3},"N_L_ID":"241f7ef842174562ba7eb947ccb43c24","N_V_ID":"5daa9b5971fe421c81dc6ccf955318a5","V_V_NAME":"3","V_V_NUM":"1","PSYJ":"1","V_V_TYPE":"4","D_V_BEGIN":"2020-01-22","PSZT":"0","ZT":"0","N_C_ID":"e0fe870f52eb4d918efa196ef7df0b85","CREATETIME":{"date":22,"hours":9,"seconds":22,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":22,"time":1579656142000,"day":3},"PSSJ":"2020-01-22"}]
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

        public static class ResBean implements Parcelable {
            /**
             * V_V_PERFOR : 2
             * UPDATETIME : {"date":22,"hours":9,"seconds":22,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":22,"time":1579656142000,"day":3}
             * N_L_ID : 241f7ef842174562ba7eb947ccb43c24
             * N_V_ID : 5daa9b5971fe421c81dc6ccf955318a5
             * V_V_NAME : 3
             * V_V_NUM : 1
             * PSYJ : 1
             * V_V_TYPE : 4
             * D_V_BEGIN : 2020-01-22
             * PSZT : 0
             * ZT : 0
             * N_C_ID : e0fe870f52eb4d918efa196ef7df0b85
             * CREATETIME : {"date":22,"hours":9,"seconds":22,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":22,"time":1579656142000,"day":3}
             * PSSJ : 2020-01-22
             */

            private String V_V_PERFOR;
            private UPDATETIMEBean UPDATETIME;
            private String N_L_ID;
            private String N_V_ID;
            private String V_V_NAME;
            private String V_V_NUM;
            private String PSYJ;
            private String V_V_TYPE;
            private String D_V_BEGIN;
            private String PSZT;
            private String ZT;
            private String N_C_ID;
            private CREATETIMEBean CREATETIME;
            private String PSSJ;

            public String getV_V_PERFOR() {
                return V_V_PERFOR;
            }

            public void setV_V_PERFOR(String V_V_PERFOR) {
                this.V_V_PERFOR = V_V_PERFOR;
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

            public String getN_V_ID() {
                return N_V_ID;
            }

            public void setN_V_ID(String N_V_ID) {
                this.N_V_ID = N_V_ID;
            }

            public String getV_V_NAME() {
                return V_V_NAME;
            }

            public void setV_V_NAME(String V_V_NAME) {
                this.V_V_NAME = V_V_NAME;
            }

            public String getV_V_NUM() {
                return V_V_NUM;
            }

            public void setV_V_NUM(String V_V_NUM) {
                this.V_V_NUM = V_V_NUM;
            }

            public String getPSYJ() {
                return PSYJ;
            }

            public void setPSYJ(String PSYJ) {
                this.PSYJ = PSYJ;
            }

            public String getV_V_TYPE() {
                return V_V_TYPE;
            }

            public void setV_V_TYPE(String V_V_TYPE) {
                this.V_V_TYPE = V_V_TYPE;
            }

            public String getD_V_BEGIN() {
                return D_V_BEGIN;
            }

            public void setD_V_BEGIN(String D_V_BEGIN) {
                this.D_V_BEGIN = D_V_BEGIN;
            }

            public String getPSZT() {
                return PSZT;
            }

            public void setPSZT(String PSZT) {
                this.PSZT = PSZT;
            }

            public String getZT() {
                return ZT;
            }

            public void setZT(String ZT) {
                this.ZT = ZT;
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

            public String getPSSJ() {
                return PSSJ;
            }

            public void setPSSJ(String PSSJ) {
                this.PSSJ = PSSJ;
            }

            public static class UPDATETIMEBean implements Parcelable {
                /**
                 * date : 22
                 * hours : 9
                 * seconds : 22
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 22
                 * time : 1579656142000
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

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.date);
                    dest.writeInt(this.hours);
                    dest.writeInt(this.seconds);
                    dest.writeInt(this.month);
                    dest.writeInt(this.nanos);
                    dest.writeInt(this.timezoneOffset);
                    dest.writeInt(this.year);
                    dest.writeInt(this.minutes);
                    dest.writeLong(this.time);
                    dest.writeInt(this.day);
                }

                public UPDATETIMEBean() {
                }

                protected UPDATETIMEBean(Parcel in) {
                    this.date = in.readInt();
                    this.hours = in.readInt();
                    this.seconds = in.readInt();
                    this.month = in.readInt();
                    this.nanos = in.readInt();
                    this.timezoneOffset = in.readInt();
                    this.year = in.readInt();
                    this.minutes = in.readInt();
                    this.time = in.readLong();
                    this.day = in.readInt();
                }

                public static final Creator<UPDATETIMEBean> CREATOR = new Creator<UPDATETIMEBean>() {
                    @Override
                    public UPDATETIMEBean createFromParcel(Parcel source) {
                        return new UPDATETIMEBean(source);
                    }

                    @Override
                    public UPDATETIMEBean[] newArray(int size) {
                        return new UPDATETIMEBean[size];
                    }
                };
            }

            public static class CREATETIMEBean implements Parcelable {
                /**
                 * date : 22
                 * hours : 9
                 * seconds : 22
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 22
                 * time : 1579656142000
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

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.date);
                    dest.writeInt(this.hours);
                    dest.writeInt(this.seconds);
                    dest.writeInt(this.month);
                    dest.writeInt(this.nanos);
                    dest.writeInt(this.timezoneOffset);
                    dest.writeInt(this.year);
                    dest.writeInt(this.minutes);
                    dest.writeLong(this.time);
                    dest.writeInt(this.day);
                }

                public CREATETIMEBean() {
                }

                protected CREATETIMEBean(Parcel in) {
                    this.date = in.readInt();
                    this.hours = in.readInt();
                    this.seconds = in.readInt();
                    this.month = in.readInt();
                    this.nanos = in.readInt();
                    this.timezoneOffset = in.readInt();
                    this.year = in.readInt();
                    this.minutes = in.readInt();
                    this.time = in.readLong();
                    this.day = in.readInt();
                }

                public static final Creator<CREATETIMEBean> CREATOR = new Creator<CREATETIMEBean>() {
                    @Override
                    public CREATETIMEBean createFromParcel(Parcel source) {
                        return new CREATETIMEBean(source);
                    }

                    @Override
                    public CREATETIMEBean[] newArray(int size) {
                        return new CREATETIMEBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.V_V_PERFOR);
                dest.writeParcelable(this.UPDATETIME, flags);
                dest.writeString(this.N_L_ID);
                dest.writeString(this.N_V_ID);
                dest.writeString(this.V_V_NAME);
                dest.writeString(this.V_V_NUM);
                dest.writeString(this.PSYJ);
                dest.writeString(this.V_V_TYPE);
                dest.writeString(this.D_V_BEGIN);
                dest.writeString(this.PSZT);
                dest.writeString(this.ZT);
                dest.writeString(this.N_C_ID);
                dest.writeParcelable(this.CREATETIME, flags);
                dest.writeString(this.PSSJ);
            }

            public ResBean() {
            }

            protected ResBean(Parcel in) {
                this.V_V_PERFOR = in.readString();
                this.UPDATETIME = in.readParcelable(UPDATETIMEBean.class.getClassLoader());
                this.N_L_ID = in.readString();
                this.N_V_ID = in.readString();
                this.V_V_NAME = in.readString();
                this.V_V_NUM = in.readString();
                this.PSYJ = in.readString();
                this.V_V_TYPE = in.readString();
                this.D_V_BEGIN = in.readString();
                this.PSZT = in.readString();
                this.ZT = in.readString();
                this.N_C_ID = in.readString();
                this.CREATETIME = in.readParcelable(CREATETIMEBean.class.getClassLoader());
                this.PSSJ = in.readString();
            }

            public static final Creator<ResBean> CREATOR = new Creator<ResBean>() {
                @Override
                public ResBean createFromParcel(Parcel source) {
                    return new ResBean(source);
                }

                @Override
                public ResBean[] newArray(int size) {
                    return new ResBean[size];
                }
            };
        }
    }
}
