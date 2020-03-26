package com.hulian.oa.utils;

import android.text.format.Time;

import com.hulian.oa.bean.People;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class TimeUtils {
    private static SimpleDateFormat sf = null;


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowTime() {
        String timeString = null;
        Time time = new Time();
        time.setToNow();
        String year = thanTen(time.year);
        String month = thanTen(time.month + 1);
        String monthDay = thanTen(time.monthDay);
        String hour = thanTen(time.hour);
        String minute = thanTen(time.minute);

        timeString = year + "-" + month + "-" + monthDay/* + " " + hour + ":" + minute*/;
        // System.out.println("-------timeString----------" + timeString);
        return timeString;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowhousr() {
        String timeString = null;
        Time time = new Time();
        time.setToNow();
        String year = thanTen(time.year);
        String month = thanTen(time.month + 1);
        String monthDay = thanTen(time.monthDay);
        String hour = thanTen(time.hour);
        String minute = thanTen(time.minute);

        timeString = hour + ":" + minute;
        // System.out.println("-------timeString----------" + timeString);
        return timeString;
    }

    public static String getMway() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return mWay;
    }

    /**
     * 获取到分
     *
     * @return
     */
    public static String getNowTime1() {
        String timeString = null;
        Time time = new Time();
        time.setToNow();
        String year = thanTen(time.year);
        String month = thanTen(time.month + 1);
        String monthDay = thanTen(time.monthDay);
        String hour = thanTen(time.hour);
        String minute = thanTen(time.minute);

        timeString = year + "-" + month + "-" + monthDay + " " + hour + ":" + minute;
        // System.out.println("-------timeString----------" + timeString);
        return timeString;
    }

    public int calculate(int year, int month) {

        boolean yearleap = judge(year);
        int day;
        if (yearleap && month == 2) {
            day = 29;
        } else if (!yearleap && month == 2) {
            day = 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            day = 30;
        } else {
            day = 31;
        }
        return day;
    }

    public boolean judge(int year) {
        boolean yearleap = (year % 400 == 0) || (year % 4 == 0)
                && (year % 100 != 0);// 采用布尔数据计算判断是否能整除
        return yearleap;
    }

    /**
     * 十一下加零
     *
     * @param str
     * @return
     */
    public static String thanTen(int str) {

        String string = null;

        if (str < 10) {
            string = "0" + str;
        } else {

            string = "" + str;

        }
        return string;
    }

    /**
     * 计算时间差
     *
     * @param starTime 开始时间
     * @param endTime  结束时间
     * @param
     * @return 返回时间差
     */
    public static String getTimeDifference(String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

//        try {
//            Date parse = null;
//            try {
//                parse = dateFormat.parse(starTime);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Date parse1 = dateFormat.parse(endTime);
//
//            long diff = parse1.getTime() - parse.getTime();
//
//            long day = diff / (24 * 60 * 60 * 1000);
//            long hour = (diff / (60 * 60 * 1000) - day * 24);
//            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
//            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
//                    - min * 60 * 1000 - s * 1000);
//            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
//            // "秒");
//            long hour1 = diff / (60 * 60 * 1000);
//            String hourString = hour1 + "";
//            long min1 = ((diff / (60 * 1000)) - hour1 * 60);
////            timeString = hour1 + "小时" + min1 + "分";
//            if(min>0||hour>0||day>0){
//                if(hour<=4){
//                    if(hour==4 && min>0){
//                        timeString = day+1 + "天"+ hour + "小时" + min + "分";
//                    }else{
//                        timeString = day+0.5 + "天"+ hour + "小时" + min + "分";
//                    }
//                }else if(hour<=8 && hour>4){
//                    timeString = day+1 + "天"+ hour + "小时" + min + "分";
//                }else if(hour>8){
//                    timeString = day+1 + "天"+ hour + "小时" + min + "分";
//                }
//            }else{
//                timeString = day + "天"+ hour + "小时" + min + "分";
//            }
//
//            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
//            // "秒");
//
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        String result = "";
        try {
            Date now = dateFormat.parse(starTime);
            Date date = dateFormat.parse(endTime);
            long l = date.getTime() - now.getTime();       //获取时间差
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            //    System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
            result = "" + day + "天" + hour + "小时" + min + "分";
            if (day == 0)
                result = result.split("天")[1].toString();
            else if (hour == 0)
                result = result.split("小时")[1].toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeString;

    }

    public static int differentDaysByMillisecond(String date1, String date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int days = 0;
        try {
            days = (int) ((dateFormat.parse(date2).getTime() - dateFormat.parse(date1).getTime()) / (1000 * 3600 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    public static int differentDaysByMillisecond2(String date1, String date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM:dd");

        int days = 0;
        try {
            days = (int) ((dateFormat.parse(date2).getTime() - dateFormat.parse(date1).getTime()) / (1000 * 3600 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 计算相差的小时(返回小时)
     *
     * @param starTime
     * @param endTime
     * @return
     */
    public static String getTimeDifferenceHour(String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();
            String string = Long.toString(diff);

            float parseFloat = Float.parseFloat(string);

            float hour1 = parseFloat / (60 * 60 * 1000);

            timeString = Float.toString(hour1);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timeString;

    }

    /**
     * 获取时间中的某一个时间点
     *
     * @param str
     * @param type
     * @return
     */
    public String getJsonParseShiJian(String str, int type) {
        String shijanString = null;
        String nian = str.substring(0, str.indexOf("-"));
        String yue = str.substring(str.indexOf("-") + 1, str.lastIndexOf("-"));
        String tian = str.substring(str.lastIndexOf("-") + 1, str.indexOf(" "));
        String shi = str.substring(str.indexOf(" ") + 1, str.lastIndexOf(":"));
        String fen = str.substring(str.lastIndexOf(":") + 1, str.length());

        switch (type) {
            case 1:
                shijanString = nian;
                break;
            case 2:
                shijanString = yue;
                break;
            case 3:
                shijanString = tian;
                break;
            case 4:
                shijanString = shi;
                break;
            case 5:
                shijanString = fen;
                break;

        }
        return shijanString;
    }

    /**
     * Sring变int
     *
     * @param str
     * @return
     */
    public int strToInt(String str) {
        int value = 0;
        value = Integer.parseInt(str);
        return value;
    }

    /**
     * 与当前时间比较早晚
     *
     * @param time 需要比较的时间
     * @return 输入的时间比现在时间晚则返回true
     */
    public static boolean compareNowTime(String time) {
        boolean isDayu = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date parse = dateFormat.parse(time);
            Date parse1 = dateFormat.parse(getNowTime());

            long diff = parse1.getTime() - parse.getTime();
            if (diff < 0) {
                isDayu = true;
            } else {
                isDayu = false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isDayu;
    }

    /**
     * 判断是否大于当前时间（到分）
     *
     * @param time
     * @return
     */
    public static boolean compareNowTime1(String time) {
        boolean isDayu = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(time);
            Date parse1 = dateFormat.parse(getNowTime1());

            long diff = parse1.getTime() - parse.getTime();
            if (diff <= 0) {
                isDayu = true;
            } else {
                isDayu = false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isDayu;
    }

    /**
     * 转换时间日期格式字串为long型
     *
     * @param time 格式为：yyyy-MM-dd HH:mm:ss的时间日期类型
     */
    public static Long convertTimeToLong(String time) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 把时间戳变MM-dd HH:mm格式时间
     *
     * @param time
     * @return
     */
    public static String getDateToString(String time) {
        //   Date d = new Date(convertTimeToLong(hb_time));

        //  return sf.format(d);

        String daynow = getDateToString4(time);
        sf = new SimpleDateFormat("MM-dd HH:mm");
        Date d = new Date(convertTimeToLong(time));
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        if (Integer.parseInt(daynow) == day) {
            return sf.format(d).split(" ")[1] + "";
        } else {
            return sf.format(d);
        }
    }

    /**
     * 把时间戳变MM-dd HH:mm格式时间
     *
     * @param time
     * @return
     */
    public static String getDateToString0(String time) {
        sf = new SimpleDateFormat("MM-dd HH:mm");
        Date d = new Date(convertTimeToLong(time));
        return sf.format(d);
    }

    public static String getDateToString1(String time) {
        sf = new SimpleDateFormat("HH:mm");
        Date d = new Date(convertTimeToLong(time));
        return sf.format(d);
    }

    public static String getDateToString6(String time) {
        sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(convertTimeToLong(time));
        return sf.format(d);
    }

    /**
     * 把时间戳变yyyy/MM格式时间
     *
     * @param time
     * @return
     */
    public static String getDateToString3(String time) {
        Date d = new Date(convertTimeToLong(time));
        sf = new SimpleDateFormat("yyyy/MM");
        return sf.format(d);
    }

    /**
     * 把时间戳变dd格式时间
     *
     * @param time
     * @return
     */
    public static String getDateToString4(String time) {
        Date d = new Date(convertTimeToLong(time));
        sf = new SimpleDateFormat("dd");
        return sf.format(d);
    }


    /**
     * 把时间戳变MM-dd HH:mm格式时间
     *
     * @param time
     * @return
     */
    public static String getDateToString2(String time) {
        Date d = new Date(convertTimeToLong(time));
        sf = new SimpleDateFormat("HH:mm");
        return sf.format(d);
    }

    /**
     * 把时间戳变HH格式时间
     *
     * @param time
     * @return
     */
    public static String getDateToString5(String time) {
        Date d = new Date(convertTimeToLong(time));
        sf = new SimpleDateFormat("HH");
        return sf.format(d);
    }

    /**
     * list集合去重：
     * 把list里的对象遍历一遍，用list.contain()，如果不存在就放入到另外一个list集合中
     */
    public static List removeDuplicate(List list) {
        List listTemp = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (!listTemp.contains(list.get(i))) {
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }

    /**
     * 返回时间戳
     *
     * @param time
     * @return
     */
    public long dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.CHINA);
        Date date;
        long l = 0;
        try {
            date = sdr.parse(time);
            l = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * 返回时间MM-dd HH:mm
     *
     * @param time
     * @return
     */
    public static String MMtime(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd HH:mm",
                Locale.CHINA);
        Date date;
        long l = 0;
        try {
            date = sdr.parse(time);
            l = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l + "";
    }

    /**
     * 比较两个时间
     *
     * @param starTime  开始时间
     * @param endString 结束时间
     * @return 结束时间大于开始时间返回true，否则反之֮
     */
    public static boolean compareTwoTime(String starTime, String endString) {
        boolean isDayu = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endString);

            long diff = parse1.getTime() - parse.getTime();
            if (diff >= 0) {
                isDayu = true;
            } else {
                isDayu = false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isDayu;

    }

    public static boolean compareTwoTime2(String starTime, String endString) {
        boolean isDayu = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endString);

            long diff = parse1.getTime() - parse.getTime();
            if (diff >= 0) {
                isDayu = true;
            } else {
                isDayu = false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isDayu;

    }

    /**
     * 获取年
     *
     * @param time
     * @return
     */
    public String getTimeYear(String time) {

        String substring = time.substring(0, time.lastIndexOf(" "));
        return substring;

    }

    /**
     * 换算小时，0.5小时-->0小时30分
     *
     * @param hour
     * @return
     */
    private String convertTime(String hour) {

        String substring = hour.substring(0, hour.lastIndexOf("."));
        String substring2 = hour.substring(hour.lastIndexOf(".") + 1,
                hour.length());
        substring2 = "0." + substring2;
        float f2 = Float.parseFloat(substring2);
        f2 = f2 * 60;
        String string = Float.toString(f2);
        String min = string.substring(0, string.lastIndexOf("."));
        return substring + "小时" + min + "分";
    }

    // 删除ArrayList中重复元素，保持顺序
    public static List removeDuplicateWithOrder(List<People> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getUserId().equals(list.get(i).getUserId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }


    /**
     * 判断2个时间大小
     * yyyy-MM-dd HH:mm 格式（自己可以修改成想要的时间格式）
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int timeCompare(String startTime, String endTime) {
        int i = 0;
        //注意：传过来的时间格式必须要和这里填入的时间格式相同
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime() < date1.getTime()) {
                //结束时间小于开始时间
                i = 1;
            } else if (date2.getTime() == date1.getTime()) {
                //开始时间与结束时间相同
                i = 2;
            } else if (date2.getTime() > date1.getTime()) {
                //结束时间大于开始时间
                i = 3;
            }
        } catch (Exception e) {

        }
        return i;
    }

    /**
     * 时间戳转换
     * @param seconds
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

}
