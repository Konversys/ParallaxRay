package ru.konverdev.parallax.model.yandex_api;

import com.squareup.moshi.Json;

public class Segment {
    @Json(name = "arrival")
    private String arrival;
    @Json(name = "thread")
    private Thread thread;
    @Json(name = "departure")
    private String departure;
    @Json(name = "duration")
    private double duration;
    @Json(name = "start_date")
    private String startDate;

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
