package mobi.mixiong.util;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY年MM月DD日HHMMSS = "yyyy年MM月dd日 HH:mm:ss";

    public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";

    public static final String DD小时HH分MM秒 = " HH小时mm分ss秒";

    public static final String YYYY年MM月DD日 = "YYYY年mm月DD日";

    public static final String DDHHMM = "";

    public static final Long oneMinute = 60 * 1000L;

    public static final Long oneHour = oneMinute * 60;

    public static final Long oneDay = oneHour * 24;

    public static String dateFormat(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String dateFormat(Long times, String pattern) {
        return dateFormat(new Date(times), pattern);
    }

    public static String getMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        return format.format(date);
    }

    public static Date getMonth() {
        Calendar calendar = Calendar.getInstance();
        return getMonth(calendar);
    }

    public static Date getMonth(int monthAdd) {
        Calendar calendar = Calendar.getInstance();
        return getMonth(calendar, monthAdd);
    }

    public static Date getMonthLastDay(int monthAdd) {
        Calendar calendar = Calendar.getInstance();
        return getMonthLastDay(calendar, monthAdd);
    }

    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        return getToday(calendar);
    }

    public static Date getMonth(Calendar today) {
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        today.set(Calendar.DAY_OF_MONTH, today.getActualMinimum(Calendar.DAY_OF_MONTH));
        return today.getTime();
    }

    public static Date getMonthLastDay(Calendar today, int monthAdd) {
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);
        today.set(Calendar.MILLISECOND, 999);
        today.set(Calendar.DAY_OF_MONTH, today.getActualMaximum(Calendar.DAY_OF_MONTH));
        today.add(Calendar.MONTH, monthAdd);
        return today.getTime();
    }

    public static Date getMonth(Calendar today, int monthAdd) {
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        today.set(Calendar.DAY_OF_MONTH, today.getActualMinimum(Calendar.DAY_OF_MONTH));
        today.add(Calendar.MONTH, monthAdd);
        return today.getTime();
    }

    public static Date getToday(Calendar today) {
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today.getTime();
    }

    public static long getCouponEndTime(long endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(endTime));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis();
    }

    public static Integer compareDays(Long time1, Long time2) {
        if (time1 >= time2) {
            return 0;
        } else {
            DateTime dt1 = new DateTime(time1);
            DateTime dt2 = new DateTime(time2);
            return Math.max(1, Days.daysBetween(dt1, dt2).getDays());
        }
    }

    public static String getCountDown(Long time) {
        time = time / 1000;
        boolean flag = false;
        StringBuilder builder = new StringBuilder();
        Long day = time / 60 / 60 / 24;
        Long hour = time / 60 / 60 % 24;
        Long minute = time / 60 % 60;
        Long second = time % 60;

        if (day != 0) {
            builder.append(day + "天");
            flag = true;
        }

        if (hour != 0 || flag) {
            builder.append(hour + "小时");
            flag = true;
        }

        if (minute != 0 || flag) {
            builder.append(minute + "分");
            flag = true;
        }

        if (second != 0 || flag) {
            builder.append(second + "秒");
            flag = true;
        }
        return flag ? builder.toString() : "0秒";
    }

    public static void main(String[] args) {
        System.out.println(getCountDown(2201192L));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(formatter.format(getToday()));
    }
}
