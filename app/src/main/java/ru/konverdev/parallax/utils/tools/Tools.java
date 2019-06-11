package ru.konverdev.parallax.utils.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import ru.konverdev.parallax.model.classes.Direction;
import ru.konverdev.parallax.model.classes.Wagon;


public class Tools {
    public static Date getNowMSK() throws ParseException {
        SimpleDateFormat dateFormatMSK = new SimpleDateFormat(TimeConverter.DATE_LINE_YEAR_SMONTH_DAY_TIME, Locale.US);
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat(TimeConverter.DATE_LINE_YEAR_SMONTH_DAY_TIME, Locale.US);
        dateFormatMSK.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return dateFormatLocal.parse(dateFormatMSK.format(new Date()));
    }

    public static boolean IsFlight() {
        return Direction.GetSelectedDirection() != null && Wagon.GetWagon() != null &&
                Wagon.GetWagon().getCoupes() != null && !Wagon.GetWagon().getCoupes().isEmpty();
    }
}
