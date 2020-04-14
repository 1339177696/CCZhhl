package com.hulian.oa.iac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ApproveBean {

    /**
     * obj : {"res":[{"V_YEAR":"1","UPDATETIME":{"date":2,"hours":17,"seconds":14,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":17,"time":1577956634000,"day":4},"V_CC_ORGANIZATION":"认证机构","D_CC_BEGIN_DATE":"2020-01-02","V_CC_ITEM":"认证项目","N_CC_ID":"f9d8628cd8ce44e08b62cee95a126f6f","N_C_ID":"cd4d7253f77f418c96ff65b6c5b4b22c","CREATETIME":{"date":2,"hours":17,"seconds":14,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":17,"time":1577956634000,"day":4}}],"N_L_ID":"af3a16d5d2f041859be47db8476800d8","xklx":"1","N_B_ID":"cd4d7253f77f418c96ff65b6c5b4b22c"}
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
         * res : [{"V_YEAR":"1","UPDATETIME":{"date":2,"hours":17,"seconds":14,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":17,"time":1577956634000,"day":4},"V_CC_ORGANIZATION":"认证机构","D_CC_BEGIN_DATE":"2020-01-02","V_CC_ITEM":"认证项目","N_CC_ID":"f9d8628cd8ce44e08b62cee95a126f6f","N_C_ID":"cd4d7253f77f418c96ff65b6c5b4b22c","CREATETIME":{"date":2,"hours":17,"seconds":14,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":17,"time":1577956634000,"day":4}}]
         * N_L_ID : af3a16d5d2f041859be47db8476800d8
         * xklx : 1
         * N_B_ID : cd4d7253f77f418c96ff65b6c5b4b22c
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
             * V_YEAR : 1
             * UPDATETIME : {"date":2,"hours":17,"seconds":14,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":17,"time":1577956634000,"day":4}
             * V_CC_ORGANIZATION : 认证机构
             * D_CC_BEGIN_DATE : 2020-01-02
             * V_CC_ITEM : 认证项目
             * N_CC_ID : f9d8628cd8ce44e08b62cee95a126f6f
             * N_C_ID : cd4d7253f77f418c96ff65b6c5b4b22c
             * CREATETIME : {"date":2,"hours":17,"seconds":14,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":17,"time":1577956634000,"day":4}
             */

            private String V_YEAR;
            private UPDATETIMEBean UPDATETIME;
            private String V_CC_ORGANIZATION;
            private String D_CC_BEGIN_DATE;
            private String V_CC_ITEM;
            private String N_CC_ID;
            private String N_C_ID;
            private CREATETIMEBean CREATETIME;

            public String getV_YEAR() {
                return V_YEAR;
            }

            public void setV_YEAR(String V_YEAR) {
                this.V_YEAR = V_YEAR;
            }

            public UPDATETIMEBean getUPDATETIME() {
                return UPDATETIME;
            }

            public void setUPDATETIME(UPDATETIMEBean UPDATETIME) {
                this.UPDATETIME = UPDATETIME;
            }

            public String getV_CC_ORGANIZATION() {
                return V_CC_ORGANIZATION;
            }

            public void setV_CC_ORGANIZATION(String V_CC_ORGANIZATION) {
                this.V_CC_ORGANIZATION = V_CC_ORGANIZATION;
            }

            public String getD_CC_BEGIN_DATE() {
                return D_CC_BEGIN_DATE;
            }

            public void setD_CC_BEGIN_DATE(String D_CC_BEGIN_DATE) {
                this.D_CC_BEGIN_DATE = D_CC_BEGIN_DATE;
            }

            public String getV_CC_ITEM() {
                return V_CC_ITEM;
            }

            public void setV_CC_ITEM(String V_CC_ITEM) {
                this.V_CC_ITEM = V_CC_ITEM;
            }

            public String getN_CC_ID() {
                return N_CC_ID;
            }

            public void setN_CC_ID(String N_CC_ID) {
                this.N_CC_ID = N_CC_ID;
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

            public static class UPDATETIMEBean implements Parcelable {
                /**
                 * date : 2
                 * hours : 17
                 * seconds : 14
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 17
                 * time : 1577956634000
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
                 * date : 2
                 * hours : 17
                 * seconds : 14
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 17
                 * time : 1577956634000
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
                dest.writeString(this.V_YEAR);
                dest.writeParcelable(this.UPDATETIME, flags);
                dest.writeString(this.V_CC_ORGANIZATION);
                dest.writeString(this.D_CC_BEGIN_DATE);
                dest.writeString(this.V_CC_ITEM);
                dest.writeString(this.N_CC_ID);
                dest.writeString(this.N_C_ID);
                dest.writeParcelable(this.CREATETIME, flags);
            }

            public ResBean() {
            }

            protected ResBean(Parcel in) {
                this.V_YEAR = in.readString();
                this.UPDATETIME = in.readParcelable(UPDATETIMEBean.class.getClassLoader());
                this.V_CC_ORGANIZATION = in.readString();
                this.D_CC_BEGIN_DATE = in.readString();
                this.V_CC_ITEM = in.readString();
                this.N_CC_ID = in.readString();
                this.N_C_ID = in.readString();
                this.CREATETIME = in.readParcelable(CREATETIMEBean.class.getClassLoader());
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
