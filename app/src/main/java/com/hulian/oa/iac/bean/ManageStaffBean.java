package com.hulian.oa.iac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ManageStaffBean {


    /**
     * obj : {"res":[{"V_M_REMARK":"11","UPDATETIME":{"date":22,"hours":9,"seconds":47,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656107000,"day":3},"V_M_CREDENTIALS":"0","V_M_YEAR":"89","N_M_ID":"cb0975cb4bef4fc29013b03e316274a0","N_L_ID":"241f7ef842174562ba7eb947ccb43c24","V_ZT":"0","N_COMPANY_ID":"e0fe870f52eb4d918efa196ef7df0b85","V_M_SPECIALTY":"5","PSYJ":"1","V_M_EDUCATION":"4","V_M_TITLE":"7","PSZT":"0","V_M_ITEM":"1","V_M_AGE":"3","CREATETIME":{"date":22,"hours":9,"seconds":47,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656107000,"day":3},"V_M_NAME":"2","PSSJ":"2020-01-22"}],"N_L_ID":"241f7ef842174562ba7eb947ccb43c24","xklx":"1","N_B_ID":"1ca7284011e64bb988036c05ba0bd5f3"}
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
         * res : [{"V_M_REMARK":"11","UPDATETIME":{"date":22,"hours":9,"seconds":47,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656107000,"day":3},"V_M_CREDENTIALS":"0","V_M_YEAR":"89","N_M_ID":"cb0975cb4bef4fc29013b03e316274a0","N_L_ID":"241f7ef842174562ba7eb947ccb43c24","V_ZT":"0","N_COMPANY_ID":"e0fe870f52eb4d918efa196ef7df0b85","V_M_SPECIALTY":"5","PSYJ":"1","V_M_EDUCATION":"4","V_M_TITLE":"7","PSZT":"0","V_M_ITEM":"1","V_M_AGE":"3","CREATETIME":{"date":22,"hours":9,"seconds":47,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656107000,"day":3},"V_M_NAME":"2","PSSJ":"2020-01-22"}]
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
             * V_M_REMARK : 11
             * UPDATETIME : {"date":22,"hours":9,"seconds":47,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656107000,"day":3}
             * V_M_CREDENTIALS : 0
             * V_M_YEAR : 89
             * N_M_ID : cb0975cb4bef4fc29013b03e316274a0
             * N_L_ID : 241f7ef842174562ba7eb947ccb43c24
             * V_ZT : 0
             * N_COMPANY_ID : e0fe870f52eb4d918efa196ef7df0b85
             * V_M_SPECIALTY : 5
             * PSYJ : 1
             * V_M_EDUCATION : 4
             * V_M_TITLE : 7
             * PSZT : 0
             * V_M_ITEM : 1
             * V_M_AGE : 3
             * CREATETIME : {"date":22,"hours":9,"seconds":47,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":21,"time":1579656107000,"day":3}
             * V_M_NAME : 2
             * PSSJ : 2020-01-22
             */

            private String V_M_REMARK;
            private UPDATETIMEBean UPDATETIME;
            private String V_M_CREDENTIALS;
            private String V_M_YEAR;
            private String N_M_ID;
            private String N_L_ID;
            private String V_ZT;
            private String N_COMPANY_ID;
            private String V_M_SPECIALTY;
            private String PSYJ;
            private String V_M_EDUCATION;
            private String V_M_TITLE;
            private String PSZT;
            private String V_M_ITEM;
            private String V_M_AGE;
            private CREATETIMEBean CREATETIME;
            private String V_M_NAME;
            private String PSSJ;

            public String getV_M_REMARK() {
                return V_M_REMARK;
            }

            public void setV_M_REMARK(String V_M_REMARK) {
                this.V_M_REMARK = V_M_REMARK;
            }

            public UPDATETIMEBean getUPDATETIME() {
                return UPDATETIME;
            }

            public void setUPDATETIME(UPDATETIMEBean UPDATETIME) {
                this.UPDATETIME = UPDATETIME;
            }

            public String getV_M_CREDENTIALS() {
                return V_M_CREDENTIALS;
            }

            public void setV_M_CREDENTIALS(String V_M_CREDENTIALS) {
                this.V_M_CREDENTIALS = V_M_CREDENTIALS;
            }

            public String getV_M_YEAR() {
                return V_M_YEAR;
            }

            public void setV_M_YEAR(String V_M_YEAR) {
                this.V_M_YEAR = V_M_YEAR;
            }

            public String getN_M_ID() {
                return N_M_ID;
            }

            public void setN_M_ID(String N_M_ID) {
                this.N_M_ID = N_M_ID;
            }

            public String getN_L_ID() {
                return N_L_ID;
            }

            public void setN_L_ID(String N_L_ID) {
                this.N_L_ID = N_L_ID;
            }

            public String getV_ZT() {
                return V_ZT;
            }

            public void setV_ZT(String V_ZT) {
                this.V_ZT = V_ZT;
            }

            public String getN_COMPANY_ID() {
                return N_COMPANY_ID;
            }

            public void setN_COMPANY_ID(String N_COMPANY_ID) {
                this.N_COMPANY_ID = N_COMPANY_ID;
            }

            public String getV_M_SPECIALTY() {
                return V_M_SPECIALTY;
            }

            public void setV_M_SPECIALTY(String V_M_SPECIALTY) {
                this.V_M_SPECIALTY = V_M_SPECIALTY;
            }

            public String getPSYJ() {
                return PSYJ;
            }

            public void setPSYJ(String PSYJ) {
                this.PSYJ = PSYJ;
            }

            public String getV_M_EDUCATION() {
                return V_M_EDUCATION;
            }

            public void setV_M_EDUCATION(String V_M_EDUCATION) {
                this.V_M_EDUCATION = V_M_EDUCATION;
            }

            public String getV_M_TITLE() {
                return V_M_TITLE;
            }

            public void setV_M_TITLE(String V_M_TITLE) {
                this.V_M_TITLE = V_M_TITLE;
            }

            public String getPSZT() {
                return PSZT;
            }

            public void setPSZT(String PSZT) {
                this.PSZT = PSZT;
            }

            public String getV_M_ITEM() {
                return V_M_ITEM;
            }

            public void setV_M_ITEM(String V_M_ITEM) {
                this.V_M_ITEM = V_M_ITEM;
            }

            public String getV_M_AGE() {
                return V_M_AGE;
            }

            public void setV_M_AGE(String V_M_AGE) {
                this.V_M_AGE = V_M_AGE;
            }

            public CREATETIMEBean getCREATETIME() {
                return CREATETIME;
            }

            public void setCREATETIME(CREATETIMEBean CREATETIME) {
                this.CREATETIME = CREATETIME;
            }

            public String getV_M_NAME() {
                return V_M_NAME;
            }

            public void setV_M_NAME(String V_M_NAME) {
                this.V_M_NAME = V_M_NAME;
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
                 * seconds : 47
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 21
                 * time : 1579656107000
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
                 * seconds : 47
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 21
                 * time : 1579656107000
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
                dest.writeString(this.V_M_REMARK);
                dest.writeParcelable(this.UPDATETIME, flags);
                dest.writeString(this.V_M_CREDENTIALS);
                dest.writeString(this.V_M_YEAR);
                dest.writeString(this.N_M_ID);
                dest.writeString(this.N_L_ID);
                dest.writeString(this.V_ZT);
                dest.writeString(this.N_COMPANY_ID);
                dest.writeString(this.V_M_SPECIALTY);
                dest.writeString(this.PSYJ);
                dest.writeString(this.V_M_EDUCATION);
                dest.writeString(this.V_M_TITLE);
                dest.writeString(this.PSZT);
                dest.writeString(this.V_M_ITEM);
                dest.writeString(this.V_M_AGE);
                dest.writeParcelable(this.CREATETIME, flags);
                dest.writeString(this.V_M_NAME);
                dest.writeString(this.PSSJ);
            }

            public ResBean() {
            }

            protected ResBean(Parcel in) {
                this.V_M_REMARK = in.readString();
                this.UPDATETIME = in.readParcelable(UPDATETIMEBean.class.getClassLoader());
                this.V_M_CREDENTIALS = in.readString();
                this.V_M_YEAR = in.readString();
                this.N_M_ID = in.readString();
                this.N_L_ID = in.readString();
                this.V_ZT = in.readString();
                this.N_COMPANY_ID = in.readString();
                this.V_M_SPECIALTY = in.readString();
                this.PSYJ = in.readString();
                this.V_M_EDUCATION = in.readString();
                this.V_M_TITLE = in.readString();
                this.PSZT = in.readString();
                this.V_M_ITEM = in.readString();
                this.V_M_AGE = in.readString();
                this.CREATETIME = in.readParcelable(CREATETIMEBean.class.getClassLoader());
                this.V_M_NAME = in.readString();
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
