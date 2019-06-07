package ru.konverdev.parallax.utils.tools;

import org.joda.time.Duration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter {
    public static final String DATE_DAY_FMONTH_YEAR_TIME  = "dd MMM yyyy HH:mm:ss";
    public static final String DATE_LINE_YEAR_SMONTH_DAY_TIME  = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_LINE_YEAR_SMONTH_DAY  = "yyyy-MM-dd";
    public static final String DATE_DOTS_DAY_SMONTH_YEAR  = "dd.MM.yyyy";
    private static final String DAYS_2C = "%02dд. ";
    private static final String HOURS_2C = "%02dч. ";
    private static final String MINUTES_2C = "%02dм. ";
    private static final String SECONDS_2C = "%02dс. ";
    private static final String ERROR_DIFFERENCE = "Неизвестно";
    private static final int HOURS_IN_DAY = 24;
    private static final int TIME_DISUNITY = 60;

    public static Date getDate(String date, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(date);
    }

    public static Date getDate(Long date) {
        return new Date(date);
    }

    public static String getStringDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String getStringDate(Long dateTime, String pattern) {
        return new SimpleDateFormat(pattern).format(new Date(dateTime));
    }

    public static Duration getDifferenceOfStationDates(Date date, Date subject) {
        if (date == null || subject == null)
            return null;
        return new Duration(date.getTime(), subject.getTime());
    }

    public static String getDifference(Date date, Date subject) {
        if (date == null || subject == null)
            return null;
        Duration duration = new Duration(date.getTime(), subject.getTime());
        if (duration == null)
            return ERROR_DIFFERENCE;
        StringBuilder result = new StringBuilder();

        long sec = duration.getStandardSeconds();
        long days = sec / TIME_DISUNITY / TIME_DISUNITY / HOURS_IN_DAY;
        long hours = sec / TIME_DISUNITY / TIME_DISUNITY - days * HOURS_IN_DAY;
        long minutes = sec / TIME_DISUNITY - days * HOURS_IN_DAY * TIME_DISUNITY - hours * TIME_DISUNITY;
        long seconds = sec - days * HOURS_IN_DAY * TIME_DISUNITY * TIME_DISUNITY - hours * TIME_DISUNITY * TIME_DISUNITY - minutes * TIME_DISUNITY;

        if (days != 0)
            result.append(String.format(DAYS_2C, days));
        if (days != 0 || hours != 0)
            result.append(String.format(HOURS_2C, hours));
        result.append(String.format(MINUTES_2C, minutes));
        result.append(String.format(SECONDS_2C, seconds));
        return result.toString();
    }
}
