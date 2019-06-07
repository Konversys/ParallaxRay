package ru.konverdev.parallax.model.yandex_api;

import com.squareup.moshi.Json;

import java.util.List;

public class Direction {
    @Json(name = "segments")
    private List<Segment> segments = null;

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }
}
