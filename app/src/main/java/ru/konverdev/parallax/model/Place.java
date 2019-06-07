package ru.konverdev.parallax.model;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Place extends RealmObject {
    @PrimaryKey
    String id;
    int number;
    Station from;
    Station to;
    boolean withLinen;
    boolean withAnimal;
    boolean withChildren;
    RealmList<Inventory> inventories;

    public void clean() {
        from = null;
        to = null;
        withLinen = false;
        withAnimal = false;
        withChildren = false;
        inventories.clear();
    }

    public Place() {
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

    public boolean isTaken() {
        if (from != null && to != null) {
            return true;
        } else {
            return false;
        }
    }

    public Station getFrom() {
        return from;
    }

    public void setFrom(Station from) {
        this.from = from;
    }

    public Station getTo() {
        return to;
    }

    public void setTo(Station to) {
        this.to = to;
    }

    public boolean isWithLinen() {
        return withLinen;
    }

    public void setWithLinen(boolean withLinen) {
        this.withLinen = withLinen;
    }

    public boolean isWithAnimal() {
        return withAnimal;
    }

    public void setWithAnimal(boolean withAnimal) {
        this.withAnimal = withAnimal;
    }

    public boolean isWithChildren() {
        return withChildren;
    }

    public void setWithChildren(boolean withChildren) {
        this.withChildren = withChildren;
    }

    public RealmList<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(RealmList<Inventory> inventories) {
        this.inventories = inventories;
    }

    public static void SetPlace(Place place){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(place);
            }
        });
    }
}

