package ru.konverdev.parallax.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.annotations.PrimaryKey;

public class Station extends RealmObject {
    public static final int BEFORE_NOW = 1;
    public static final int AFTER_NOW = 2;
    public static final int STAY = 3;

    @PrimaryKey
    private String id;
    private int number;
    private String title;
    private Date arrival;
    private Date departure;
    private String station_type_name;
    private String code;
    private int stop_time;
    private long duration;
    private int position;

    public Station(int number, String title, String station_type_name, String code, int stop_time, long duration) {
        this.id = UUID.randomUUID().toString();
        this.number = number;
        this.title = title;
        this.station_type_name = station_type_name;
        this.code = code;
        this.stop_time = stop_time;
        this.duration = duration;
    }

    public Station() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public String getStation_type_name() {
        return station_type_name;
    }

    public void setStation_type_name(String station_type_name) {
        this.station_type_name = station_type_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStop_time() {
        return stop_time;
    }

    public void setStop_time(int stop_time) {
        this.stop_time = stop_time;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static ArrayList<Station> GetStations() {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Station> stations = new ArrayList<Station>(realm.copyFromRealm(realm.where(Station.class).findAll()));
        realm.close();
        return stations;
    }

    public static ArrayList<Station> GetStationsByName(String search) {
        return GetStationsByName(search, 0);
    }

    public static ArrayList<Station> GetStationsByName(String search, Integer count) {
        ArrayList<Station> stations;
        String[] values = search.split(" ");
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Station> query = realm.where(Station.class);
        for (String line : values) {
            query = query.contains("title", line).and();
        }
        if (count <= 0) {
            stations = new ArrayList<>(realm.copyFromRealm(query.findAll()));
        } else {
            stations = new ArrayList<>(realm.copyFromRealm(query.findAll()));
            if (stations.size() > count) {
                stations = new ArrayList<>(stations.subList(0, count));
            }
        }
        realm.close();
        return stations;
    }

    public static Station GetStationsByID(int id) {
        Station station;
        Realm realm = Realm.getDefaultInstance();
        station = realm.copyFromRealm(realm.where(Station.class).equalTo("id", id).findFirst());
        realm.close();
        return station;
    }
}
