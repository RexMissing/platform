package org.whut.meterFrameManagement.util.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhang_minzhong on 2016/12/20.
 */
public class DateUtil {
    public static Timestamp createDate(String s){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = df.parse(s);
        } catch (ParseException e) {
            System.out.println("日起产生出错！");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

    /**
     * 改变月份
     * @param date   原日期
     * @param change 月份修改值
     * @return  更改月份后的日期
     */
    public static Date changeMonth(Date date, int change) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + change);

        return calendar.getTime();
    }

    /**
     * 改变天数
     * @param date   原日期
     * @param change 天数修改值
     * @return  更改天数后的日期
     */
    public static Date changeDayOfMonth(Date date, int change) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + change);

        return calendar.getTime();
    }

    /**
     * 类似C#中，TimeSpan类的TotalDays方法
     * @param milliseconds  毫秒数
     * @return 天数
     */
    public static double getTotalDays(long milliseconds) {

        return (double) milliseconds / (24 * 3600 * 1000);
    }


}
