package ru.konverdev.parallax.model.yandex_api;

import com.squareup.moshi.Json;

public class Stop {
    @Json(name = "arrival")
    private String arrival;
    @Json(name = "departure")
    private String departure;
    @Json(name = "station")
    private Station station;
    @Json(name = "stop_time")
    private String stopTime;
    @Json(name = "duration")
    private int duration;

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
