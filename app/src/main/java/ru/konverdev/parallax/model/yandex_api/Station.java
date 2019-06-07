package ru.konverdev.parallax.model.yandex_api;

import com.squareup.moshi.Json;

public class Station {
    @Json(name = "code")
    private String code;
    @Json(name = "title")
    private String title;
    @Json(name = "station_type")
    private String stationType;
    @Json(name = "popular_title")
    private String popularTitle;
    @Json(name = "short_title")
    private String shortTitle;
    @Json(name = "transport_type")
    private String transportType;
    @Json(name = "station_type_name")
    private String stationTypeName;
    @Json(name = "type")
    private String type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public String getPopularTitle() {
        return popularTitle;
    }

    public void setPopularTitle(String popularTitle) {
        this.popularTitle = popularTitle;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getStationTypeName() {
        return stationTypeName;
    }

    public void setStationTypeName(String stationTypeName) {
        this.stationTypeName = stationTypeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
