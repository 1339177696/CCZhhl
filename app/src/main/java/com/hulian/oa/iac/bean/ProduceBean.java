package com.hulian.oa.iac.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProduceBean {


    /**
     * obj : {"res":[{"V_SHB_TYPE":"111","V_TYPE":"11","PSTPIDS":"cba8145a758343a4ad038d5b84672480,e70f80f82eb94e85928183dfca35f1a6,8d06ebb461d14335ab87769fc99a0164,af4aecc9a72a49518cc7a5b7514d3a79,d8d18769d7ff4d72b3b75ca64fd6f0ed,0a2b6f49b6674d79a0bf9e7541406771,e6b07cfcbf994ff6b912b32eef76b0de,3bef896561dd4a1eb6586122054eaafc,64c0c2caf51346a1b1527cea63255b5a,0d3d5ad03b6040f9b603bd23269ac4f1,","N_P_ID":"111","UPDATETIME":{"date":19,"hours":11,"seconds":39,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":11,"time":1579403499000,"day":0},"N_L_ID":"d78d293b45644d4e9c26cb682dc662a2","V_GC_TYPE":"111111","V_SBXH":"1111","D_END_DATE":"1899-12-30","PSYJ":"1111","V_LEVEL":"111","PSZT":"1111","ZT":"0","V_ADDRESS":"1111","N_C_ID":"111","CREATETIME":{"date":15,"hours":0,"seconds":0,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":0,"time":1579017600000,"day":3},"PSSJ":"2020-01-17"}],"N_L_ID":"d78d293b45644d4e9c26cb682dc662a2","xklx":"3","N_B_ID":"a318ffb19f91478d8aa1f4b865cbcae1"}
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
         * res : [{"V_SHB_TYPE":"111","V_TYPE":"11","PSTPIDS":"cba8145a758343a4ad038d5b84672480,e70f80f82eb94e85928183dfca35f1a6,8d06ebb461d14335ab87769fc99a0164,af4aecc9a72a49518cc7a5b7514d3a79,d8d18769d7ff4d72b3b75ca64fd6f0ed,0a2b6f49b6674d79a0bf9e7541406771,e6b07cfcbf994ff6b912b32eef76b0de,3bef896561dd4a1eb6586122054eaafc,64c0c2caf51346a1b1527cea63255b5a,0d3d5ad03b6040f9b603bd23269ac4f1,","N_P_ID":"111","UPDATETIME":{"date":19,"hours":11,"seconds":39,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":11,"time":1579403499000,"day":0},"N_L_ID":"d78d293b45644d4e9c26cb682dc662a2","V_GC_TYPE":"111111","V_SBXH":"1111","D_END_DATE":"1899-12-30","PSYJ":"1111","V_LEVEL":"111","PSZT":"1111","ZT":"0","V_ADDRESS":"1111","N_C_ID":"111","CREATETIME":{"date":15,"hours":0,"seconds":0,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":0,"time":1579017600000,"day":3},"PSSJ":"2020-01-17"}]
         * N_L_ID : d78d293b45644d4e9c26cb682dc662a2
         * xklx : 3
         * N_B_ID : a318ffb19f91478d8aa1f4b865cbcae1
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
             * V_SHB_TYPE : 111
             * V_TYPE : 11
             * PSTPIDS : cba8145a758343a4ad038d5b84672480,e70f80f82eb94e85928183dfca35f1a6,8d06ebb461d14335ab87769fc99a0164,af4aecc9a72a49518cc7a5b7514d3a79,d8d18769d7ff4d72b3b75ca64fd6f0ed,0a2b6f49b6674d79a0bf9e7541406771,e6b07cfcbf994ff6b912b32eef76b0de,3bef896561dd4a1eb6586122054eaafc,64c0c2caf51346a1b1527cea63255b5a,0d3d5ad03b6040f9b603bd23269ac4f1,
             * N_P_ID : 111
             * UPDATETIME : {"date":19,"hours":11,"seconds":39,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":11,"time":1579403499000,"day":0}
             * N_L_ID : d78d293b45644d4e9c26cb682dc662a2
             * V_GC_TYPE : 111111
             * V_SBXH : 1111
             * D_END_DATE : 1899-12-30
             * PSYJ : 1111
             * V_LEVEL : 111
             * PSZT : 1111
             * ZT : 0
             * V_ADDRESS : 1111
             * N_C_ID : 111
             * CREATETIME : {"date":15,"hours":0,"seconds":0,"month":0,"nanos":0,"timezoneOffset":-480,"year":120,"minutes":0,"time":1579017600000,"day":3}
             * PSSJ : 2020-01-17
             */

            private String V_SHB_TYPE;
            private String V_TYPE;
            private String PSTPIDS;
            private String N_P_ID;
            private UPDATETIMEBean UPDATETIME;
            private String N_L_ID;
            private String V_GC_TYPE;
            private String V_SBXH;
            private String D_END_DATE;
            private String PSYJ;
            private String V_LEVEL;
            private String PSZT;
            private String ZT;
            private String V_ADDRESS;
            private String N_C_ID;
            private CREATETIMEBean CREATETIME;
            private String PSSJ;

            public String getV_SHB_TYPE() {
                return V_SHB_TYPE;
            }

            public void setV_SHB_TYPE(String V_SHB_TYPE) {
                this.V_SHB_TYPE = V_SHB_TYPE;
            }

            public String getV_TYPE() {
                return V_TYPE;
            }

            public void setV_TYPE(String V_TYPE) {
                this.V_TYPE = V_TYPE;
            }

            public String getPSTPIDS() {
                return PSTPIDS;
            }

            public void setPSTPIDS(String PSTPIDS) {
                this.PSTPIDS = PSTPIDS;
            }

            public String getN_P_ID() {
                return N_P_ID;
            }

            public void setN_P_ID(String N_P_ID) {
                this.N_P_ID = N_P_ID;
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

            public String getV_GC_TYPE() {
                return V_GC_TYPE;
            }

            public void setV_GC_TYPE(String V_GC_TYPE) {
                this.V_GC_TYPE = V_GC_TYPE;
            }

            public String getV_SBXH() {
                return V_SBXH;
            }

            public void setV_SBXH(String V_SBXH) {
                this.V_SBXH = V_SBXH;
            }

            public String getD_END_DATE() {
                return D_END_DATE;
            }

            public void setD_END_DATE(String D_END_DATE) {
                this.D_END_DATE = D_END_DATE;
            }

            public String getPSYJ() {
                return PSYJ;
            }

            public void setPSYJ(String PSYJ) {
                this.PSYJ = PSYJ;
            }

            public String getV_LEVEL() {
                return V_LEVEL;
            }

            public void setV_LEVEL(String V_LEVEL) {
                this.V_LEVEL = V_LEVEL;
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
                 * date : 19
                 * hours : 11
                 * seconds : 39
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 11
                 * time : 1579403499000
                 * day : 0
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
                 * date : 15
                 * hours : 0
                 * seconds : 0
                 * month : 0
                 * nanos : 0
                 * timezoneOffset : -480
                 * year : 120
                 * minutes : 0
                 * time : 1579017600000
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
                dest.writeString(this.V_SHB_TYPE);
                dest.writeString(this.V_TYPE);
                dest.writeString(this.PSTPIDS);
                dest.writeString(this.N_P_ID);
                dest.writeParcelable(this.UPDATETIME, flags);
                dest.writeString(this.N_L_ID);
                dest.writeString(this.V_GC_TYPE);
                dest.writeString(this.V_SBXH);
                dest.writeString(this.D_END_DATE);
                dest.writeString(this.PSYJ);
                dest.writeString(this.V_LEVEL);
                dest.writeString(this.PSZT);
                dest.writeString(this.ZT);
                dest.writeString(this.V_ADDRESS);
                dest.writeString(this.N_C_ID);
                dest.writeParcelable(this.CREATETIME, flags);
                dest.writeString(this.PSSJ);
            }

            public ResBean() {
            }

            protected ResBean(Parcel in) {
                this.V_SHB_TYPE = in.readString();
                this.V_TYPE = in.readString();
                this.PSTPIDS = in.readString();
                this.N_P_ID = in.readString();
                this.UPDATETIME = in.readParcelable(UPDATETIMEBean.class.getClassLoader());
                this.N_L_ID = in.readString();
                this.V_GC_TYPE = in.readString();
                this.V_SBXH = in.readString();
                this.D_END_DATE = in.readString();
                this.PSYJ = in.readString();
                this.V_LEVEL = in.readString();
                this.PSZT = in.readString();
                this.ZT = in.readString();
                this.V_ADDRESS = in.readString();
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
