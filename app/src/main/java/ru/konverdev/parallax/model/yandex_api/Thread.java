package ru.konverdev.parallax.model.yandex_api;

import com.squareup.moshi.Json;

public class Thread {
    @Json(name = "uid")
    private String uid;
    @Json(name = "title")
    private String title;
    @Json(name = "number")
    private String number;
    @Json(name = "short_title")
    private String shortTitle;

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
}
