package ru.konverdev.parallax.model.classes;

import com.squareup.moshi.Json;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Inventory extends RealmObject {
    @PrimaryKey
    private String id;
    @Json(name = "title")
    private String title;
    private int count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
