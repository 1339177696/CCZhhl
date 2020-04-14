package com.hulian.oa.iac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ApplyAllowBean {


    /**
     * obj : {"res":[{"V_PRODUCTION":"代表产品（限制范围、典型产品）","UPDATETIME":{"date":6,"hours":10,"seconds":55,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":8,"time":1578276535000,"day":1},"N_L_ID":"af3a16d5d2f041859be47db8476800d8","N_LT_ID":"e12e993d19e84cdab878067d14954ce3","N_SECOND_ID":"锅炉","N_THIRD_ID":"锅炉B","V_ZT":"0","CREATETIME":{"date":2,"hours":17,"seconds":28,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":17,"time":1577956648000,"day":4},"V_PARA_ID":"额定出口压力小于等于2.5MPa的蒸汽和热水锅炉；有机热载体锅炉","N_FIRST_ID":"锅炉制造","V_UNIT":"形式试验单位"}],"N_L_ID":"af3a16d5d2f041859be47db8476800d8","xklx":"1","N_B_ID":"cd4d7253f77f418c96ff65b6c5b4b22c"}
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
         * res : [{"V_PRODUCTION":"代表产品（限制范围、典型产品）","UPDATETIME":{"date":6,"hours":10,"seconds":55,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":8,"time":1578276535000,"day":1},"N_L_ID":"af3a16d5d2f041859be47db8476800d8","N_LT_ID":"e12e993d19e84cdab878067d14954ce3","N_SECOND_ID":"锅炉","N_THIRD_ID":"锅炉B","V_ZT":"0","CREATETIME":{"date":2,"hours":17,"seconds":28,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":17,"time":1577956648000,"day":4},"V_PARA_ID":"额定出口压力小于等于2.5MPa的蒸汽和热水锅炉；有机热载体锅炉","N_FIRST_ID":"锅炉制造","V_UNIT":"形式试验单位"}]
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
            private UPDATETIMEBean UPDATETIME;
            private String N_L_ID;
            private String N_LT_ID;
            private String N_SECOND_ID;
            private String N_THIRD_ID;
            private String V_ZT;
            private CREATETIMEBean CREATETIME;
            private String V_PARA_ID;
            private String N_FIRST_ID;
            private String V_UNIT;

            public String getV_PRODUCTION() {
                return V_PRODUCTION;
            }

            public void setV_PRODUCTION(String V_PRODUCTION) {
                this.V_PRODUCTION = V_PRODUCTION;
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

            public CREATETIMEBean getCREATETIME() {
                return CREATETIME;
            }

            public void setCREATETIME(CREATETIMEBean CREATETIME) {
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

            public static class UPDATETIMEBean implements Parcelable {
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
                dest.writeString(this.V_PRODUCTION);
                dest.writeParcelable(this.UPDATETIME, flags);
                dest.writeString(this.N_L_ID);
                dest.writeString(this.N_LT_ID);
                dest.writeString(this.N_SECOND_ID);
                dest.writeString(this.N_THIRD_ID);
                dest.writeString(this.V_ZT);
                dest.writeParcelable(this.CREATETIME, flags);
                dest.writeString(this.V_PARA_ID);
                dest.writeString(this.N_FIRST_ID);
                dest.writeString(this.V_UNIT);
            }

            public ResBean() {
            }

            protected ResBean(Parcel in) {
                this.V_PRODUCTION = in.readString();
                this.UPDATETIME = in.readParcelable(UPDATETIMEBean.class.getClassLoader());
                this.N_L_ID = in.readString();
                this.N_LT_ID = in.readString();
                this.N_SECOND_ID = in.readString();
                this.N_THIRD_ID = in.readString();
                this.V_ZT = in.readString();
                this.CREATETIME = in.readParcelable(CREATETIMEBean.class.getClassLoader());
                this.V_PARA_ID = in.readString();
                this.N_FIRST_ID = in.readString();
                this.V_UNIT = in.readString();
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
