package ru.konverdev.parallax.utils.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class Tools {
    public static Date getNowMSK() throws ParseException {
        SimpleDateFormat dateFormatMSK = new SimpleDateFormat(TimeConverter.DATE_LINE_YEAR_SMONTH_DAY_TIME);
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat(TimeConverter.DATE_LINE_YEAR_SMONTH_DAY_TIME);
        dateFormatMSK.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return dateFormatLocal.parse(dateFormatMSK.format(new Date()));
    }
}
