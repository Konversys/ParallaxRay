package ru.konverdev.parallax.model.classes;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import ru.konverdev.parallax.utils.tools.Tools;

public class Station extends RealmObject {
    public static final int ATOM = -1;

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

    public static void SaveStations(final List<Station> stations) {
        stations.forEach(x -> x.id = UUID.randomUUID().toString());
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Station.class).findAll().deleteAllFromRealm();
                realm.copyToRealm(stations);
            }
        });
        realm.close();
    }

    public static void CleanStations() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Station.class).findAll().deleteAllFromRealm();
            }
        });
        realm.close();
    }

    public static ArrayList<Station> GetStations() {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Station> stations = new ArrayList<Station>(realm.copyFromRealm(realm.where(Station.class).findAll()));
        realm.close();
        return stations;
    }

    public static ArrayList<Station> FindStations(String search) {
        return FindStations(search, 0);
    }

    public static ArrayList<Station> FindStations(String search, Integer count) {
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

    public static ArrayList<Station> FindStationsAfter(String search, int number) {
        return FindStationsAfter(search, number, 0);
    }

    public static ArrayList<Station> FindStationsAfter(String search, int number, int count) {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Station> stations;
        String[] values = search.split(" ");
        RealmQuery<Station> query = realm.where(Station.class).greaterThan("number", number);
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

    public static ArrayList<Station> FindStationsAfter(int number) {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Station> stations;
        stations = new ArrayList<>(realm.copyFromRealm(realm.where(Station.class).greaterThan("number", number).findAll()));
        realm.close();
        return stations;
    }

    public static Station GetStation(int number) {
        Station station;
        Realm realm = Realm.getDefaultInstance();
        Station query = realm.where(Station.class).equalTo("number", number).findFirst();
        if (query == null) {
            realm.close();
            return null;
        } else {
            station = realm.copyFromRealm(query);
            realm.close();
        }
        return station;
    }

    public static Station GetCurrent() {
        ArrayList<Station> stations = Station.GetStations();
        int left = 0;
        int right = stations.size() - 1;
        int mid;
        Date now = new Date();
        if (stations.get(0).getDeparture().after(now)) {
            mid = 0;
            return stations.get(0);
        } else if (stations.get(stations.size() - 1).getArrival().before(now)) {
            mid = stations.size() - 1;
            return stations.get(stations.size() - 1);
        } else {
            while (!(left >= right)) {
                mid = left + (right - left) / 2;
                if (stations.get(mid).getArrival() != null &&
                        stations.get(mid).getDeparture() != null &&
                        stations.get(mid).getArrival().before(now) &&
                        stations.get(mid).getDeparture().after(now)) {
                    return stations.get(mid);
                } else if (stations.get(mid).getDeparture() != null &&
                        stations.get(mid + 1).getArrival() != null &&
                        stations.get(mid).getDeparture().before(now) &&
                        stations.get(mid + 1).getArrival().after(now)) {
                    return stations.get(mid);
                }
                if (stations.get(mid).getArrival().after(now))
                    right = mid;
                else
                    left = mid + 1;
            }
            return null;
        }
    }

    public static ArrayList<Station> RefreshStationsRatio() {
        ArrayList<Station> stations = Station.GetStations();
        Station current = GetCurrent();
        stations.stream().filter(x -> x.getNumber() < current.getNumber()).forEach(x -> x.setPosition(Station.BEFORE_NOW));
        stations.stream().filter(x -> x.getNumber() >= current.getNumber()).forEach(x -> x.setPosition(Station.AFTER_NOW));
        try {
            if (current.getArrival().after(Tools.getNowMSK()) && current.getDeparture().before(Tools.getNowMSK())) {
                stations.stream().filter(x -> x.getNumber() == current.getNumber()).forEach(x -> x.setPosition(Station.STAY));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(stations);
            }
        });
        return stations;
    }
}
