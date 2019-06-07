package ru.konverdev.parallax.model.yandex_api;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

import ru.konverdev.parallax.utils.tools.TimeConverter;

public class Stations {
    private String from;
    @Json(name = "uid")
    private String uid;
    @Json(name = "title")
    private String title;
    @Json(name = "start_time")
    private String startTime;
    @Json(name = "number")
    private String number;
    @Json(name = "short_title")
    private String shortTitle;
    @Json(name = "days")
    private String days;
    @Json(name = "stops")
    private List<Stop> stops = null;
    @Json(name = "start_date")
    private String startDate;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public ArrayList<ru.konverdev.parallax.model.Station> GetStations() {
        ArrayList<ru.konverdev.parallax.model.Station> stations = new ArrayList<>();
        int count = 0;
        for (Stop station : getStops()) {
            ru.konverdev.parallax.model.Station item = new ru.konverdev.parallax.model.Station(
                    ++count,
                    station.getStation().getTitle(),
                    station.getStation().getStationTypeName(),
                    station.getStation().getCode(),
                    Integer.parseInt(station.getStopTime() == null ? "0" : station.getStopTime()),
                    station.getDuration());
            try {
                if (station.getArrival() != null)
                    item.setArrival(TimeConverter.getDate(station.getArrival(), TimeConverter.DATE_LINE_YEAR_SMONTH_DAY_TIME));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (station.getDeparture() != null)
                    item.setDeparture(TimeConverter.getDate(station.getDeparture(), TimeConverter.DATE_LINE_YEAR_SMONTH_DAY_TIME));
            } catch (Exception e) {
                e.printStackTrace();
            }
            stations.add(item);
        }
        return stations;
    }
}
