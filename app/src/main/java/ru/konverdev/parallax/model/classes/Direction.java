package ru.konverdev.parallax.model.classes;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class Direction extends RealmObject {
    @PrimaryKey
    private String id;
    @Json(name = "value")
    private String value;
    @Json(name = "name")
    private String name;
    @Json(name = "from")
    private String from;
    @Json(name = "to")
    private String to;
    private boolean selected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public static void SaveDirections(final List<Direction> directions) {
        directions.forEach(x -> x.id = UUID.randomUUID().toString());
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Direction.class).findAll().deleteAllFromRealm();
                realm.copyToRealm(directions);
            }
        });
        realm.close();
    }

    public static void CleanDirections() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Direction.class).findAll().deleteAllFromRealm();
            }
        });
        realm.close();
    }

    public static Direction GetDirection(String value) {
        Realm realm = Realm.getDefaultInstance();
        Direction query = realm.where(Direction.class).equalTo("value", value).findFirst();
        Direction direction = realm.copyFromRealm(query);
        realm.close();
        return direction;
    }

    public static Direction GetSelectedDirection() {
        Realm realm = Realm.getDefaultInstance();
        Direction query = realm.where(Direction.class).equalTo("selected", true).findFirst();
        if (query == null){
            realm.close();
            return null;
        }
        Direction direction = realm.copyFromRealm(query);
        realm.close();
        return direction;
    }

    public static Direction SetSelectedDirection(Direction direction) {
        Realm realm = Realm.getDefaultInstance();
        direction.setSelected(true);
        RealmResults<Direction> query = realm.where(Direction.class).equalTo("selected", true).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (query.size() > 0){
                    query.forEach(x -> x.selected = false);
                }
                realm.copyToRealmOrUpdate(query);
                realm.copyToRealmOrUpdate(direction);
            }
        });
        realm.close();
        return direction;
    }

    public static ArrayList<Direction> GetDirections() {
        return GetDirections(0);
    }

    public static ArrayList<Direction> GetDirections(Integer count) {
        ArrayList<Direction> directions;
        Realm realm = Realm.getDefaultInstance();
        int size = realm.where(Direction.class).findAll().size();
        if (count <= 0 || count > size) {
            directions = new ArrayList<>(realm.copyFromRealm(realm.where(Direction.class).findAll()));
        } else {
            directions = new ArrayList<>(realm.copyFromRealm(realm.where(Direction.class).findAll().subList(0, count)));
        }
        realm.close();
        return directions;
    }

    public static ArrayList<Direction> GetDirectionsByName(String search) {
        return GetDirectionsByName(search, 0);
    }

    public static ArrayList<Direction> GetDirectionsByName(String search, Integer count) {
        ArrayList<Direction> directions;
        String[] values = search.split(" ");
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Direction> query = realm.where(Direction.class);
        for (String line : values) {
            query = query.contains("name", line).and();
        }
        if (count <= 0) {
            directions = new ArrayList<>(realm.copyFromRealm(query.findAll()));
        } else {
            directions = new ArrayList<>(realm.copyFromRealm(query.findAll()));
            if (directions.size() > count) {
                directions = new ArrayList<>(directions.subList(0, count));
            }
        }
        realm.close();
        return directions;
    }
}