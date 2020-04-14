package com.hulian.oa.iac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SubcontractBean  {


    /**
     * obj : {"res":[{"UPDATETIME":{"date":21,"hours":17,"seconds":56,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":6,"time":1579597616000,"day":2},"N_S_ID":"c35254b3e5c84cd3808007d48c6618e2","N_L_ID":"8f1dd63f3f154fa496d78bfc7f05aea0","V_S_COMPANY":"吉大正元q","PSYJ":"uuu","V_S_TYPE":"无q","PSZT":"1","ZT":"0","N_C_ID":"84f0e09dcd094bed85b411919cd624ec","V_S_ITEM":"无q","CREATETIME":{"date":21,"hours":10,"seconds":13,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":16,"time":1579572973000,"day":2},"V_S_CREDITCODE":"111q","PSSJ":"2020-01-21"}],"N_L_ID":"8f1dd63f3f154fa496d78bfc7f05aea0","xklx":"1","N_B_ID":"e42116822dd94f7ea1e3cbfa407787c6"}
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
         * res : [{"UPDATETIME":{"date":21,"hours":17,"seconds":56,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":6,"time":1579597616000,"day":2},"N_S_ID":"c35254b3e5c84cd3808007d48c6618e2","N_L_ID":"8f1dd63f3f154fa496d78bfc7f05aea0","V_S_COMPANY":"吉大正元q","PSYJ":"uuu","V_S_TYPE":"无q","PSZT":"1","ZT":"0","N_C_ID":"84f0e09dcd094bed85b411919cd624ec","V_S_ITEM":"无q","CREATETIME":{"date":21,"hours":10,"seconds":13,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":16,"time":1579572973000,"day":2},"V_S_CREDITCODE":"111q","PSSJ":"2020-01-21"}]
         * N_L_ID : 8f1dd63f3f154fa496d78bfc7f05aea0
         * xklx : 1
         * N_B_ID : e42116822dd94f7ea1e3cbfa407787c6
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
             * UPDATETIME : {"date":21,"hours":17,"seconds":56,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":6,"time":1579597616000,"day":2}
             * N_S_ID : c35254b3e5c84cd3808007d48c6618e2
             * N_L_ID : 8f1dd63f3f154fa496d78bfc7f05aea0
             * V_S_COMPANY : 吉大正元q
             * PSYJ : uuu
             * V_S_TYPE : 无q
             * PSZT : 1
             * ZT : 0
             * N_C_ID : 84f0e09dcd094bed85b411919cd624ec
             * V_S_ITEM : 无q
             * CREATETIME : {"date":21,"hours":10,"seconds":13,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":16,"time":1579572973000,"day":2}
             * V_S_CREDITCODE : 111q
             * PSSJ : 2020-01-21
             */

            private UPDATETIMEBean UPDATETIME;
            private String N_S_ID;
            private String N_L_ID;
            private String V_S_COMPANY;
            private String PSYJ;
            private String V_S_TYPE;
            private String PSZT;
            private String ZT;
            private String N_C_ID;
            private String V_S_ITEM;
            private CREATETIMEBean CREATETIME;
            private String V_S_CREDITCODE;
            private String PSSJ;

            public UPDATETIMEBean getUPDATETIME() {
                return UPDATETIME;
            }

            public void setUPDATETIME(UPDATETIMEBean UPDATETIME) {
                this.UPDATETIME = UPDATETIME;
            }

            public String getN_S_ID() {
                return N_S_ID;
            }

            public void setN_S_ID(String N_S_ID) {
                this.N_S_ID = N_S_ID;
            }

            public String getN_L_ID() {
                return N_L_ID;
            }

            public void setN_L_ID(String N_L_ID) {
                this.N_L_ID = N_L_ID;
            }

            public String getV_S_COMPANY() {
                return V_S_COMPANY;
            }

            public void setV_S_COMPANY(String V_S_COMPANY) {
                this.V_S_COMPANY = V_S_COMPANY;
            }

            public String getPSYJ() {
                return PSYJ;
            }

            public void setPSYJ(String PSYJ) {
                this.PSYJ = PSYJ;
            }

            public String getV_S_TYPE() {
                return V_S_TYPE;
            }

            public void setV_S_TYPE(String V_S_TYPE) {
                this.V_S_TYPE = V_S_TYPE;
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

            public String getV_S_ITEM() {
                return V_S_ITEM;
            }

            public void setV_S_ITEM(String V_S_ITEM) {
                this.V_S_ITEM = V_S_ITEM;
            }

            public CREATETIMEBean getCREATETIME() {
                return CREATETIME;
            }

            public void setCREATETIME(CREATETIMEBean CREATETIME) {
                this.CREATETIME = CREATETIME;
            }

            public String getV_S_CREDITCODE() {
                return V_S_CREDITCODE;
            }

            public void setV_S_CREDITCODE(String V_S_CREDITCODE) {
                this.V_S_CREDITCODE = V_S_CREDITCODE;
            }

            public String getPSSJ() {
                return PSSJ;
            }

            public void setPSSJ(String PSSJ) {
                this.PSSJ = PSSJ;
            }

            public static class UPDATETIMEBean implements Parcelable {
                /**
                 * date : 21
                 * hours : 17
                 * seconds : 56
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 6
                 * time : 1579597616000
                 * day : 2
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
                 * date : 21
                 * hours : 10
                 * seconds : 13
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 16
                 * time : 1579572973000
                 * day : 2
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
                dest.writeParcelable(this.UPDATETIME, flags);
                dest.writeString(this.N_S_ID);
                dest.writeString(this.N_L_ID);
                dest.writeString(this.V_S_COMPANY);
                dest.writeString(this.PSYJ);
                dest.writeString(this.V_S_TYPE);
                dest.writeString(this.PSZT);
                dest.writeString(this.ZT);
                dest.writeString(this.N_C_ID);
                dest.writeString(this.V_S_ITEM);
                dest.writeParcelable(this.CREATETIME, flags);
                dest.writeString(this.V_S_CREDITCODE);
                dest.writeString(this.PSSJ);
            }

            public ResBean() {
            }

            protected ResBean(Parcel in) {
                this.UPDATETIME = in.readParcelable(UPDATETIMEBean.class.getClassLoader());
                this.N_S_ID = in.readString();
                this.N_L_ID = in.readString();
                this.V_S_COMPANY = in.readString();
                this.PSYJ = in.readString();
                this.V_S_TYPE = in.readString();
                this.PSZT = in.readString();
                this.ZT = in.readString();
                this.N_C_ID = in.readString();
                this.V_S_ITEM = in.readString();
                this.CREATETIME = in.readParcelable(CREATETIMEBean.class.getClassLoader());
                this.V_S_CREDITCODE = in.readString();
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
