package ru.konverdev.parallax.model;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Wagon extends RealmObject {
    public static final int COUPE = 9;

    @PrimaryKey
    String id;
    private int number;
    private int factory;
    RealmList<Coupe> coupes;

    public Wagon() {
    }

    public Wagon(String id, int number, int factory, RealmList<Coupe> coupes) {
        this.id = id;
        this.number = number;
        this.factory = factory;
        this.coupes = coupes;
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

    public int getFactory() {
        return factory;
    }

    public void setFactory(int factory) {
        this.factory = factory;
    }

    public RealmList<Coupe> getCoupes() {
        return coupes;
    }

    public void setCoupes(RealmList<Coupe> coupes) {
        this.coupes = coupes;
    }

    public void addCoupes(Coupe coupe){
        this.coupes.add(coupe);
    }

    public static void SetWagon(int countCoupe, int number, int factoryNumber) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Place.class).findAll().deleteAllFromRealm();
                realm.where(Coupe.class).findAll().deleteAllFromRealm();
                realm.where(Wagon.class).findAll().deleteAllFromRealm();
                Wagon wagon = realm.createObject(Wagon.class, UUID.randomUUID().toString());
                wagon.setNumber(number);
                wagon.setFactory(factoryNumber);
                for (int i = 0; i < countCoupe; i++) {
                    Coupe coupe = realm.createObject(Coupe.class, UUID.randomUUID().toString());
                    coupe.setNumber(i + 1);

                    Place placeLT = realm.createObject(Place.class, UUID.randomUUID().toString());
                    Place placeLB = realm.createObject(Place.class, UUID.randomUUID().toString());
                    Place placeRT = realm.createObject(Place.class, UUID.randomUUID().toString());
                    Place placeRB = realm.createObject(Place.class, UUID.randomUUID().toString());

                    placeLT.setNumber(i * 4 + 1);
                    placeLB.setNumber(i * 4 + 2);
                    placeRT.setNumber(i * 4 + 3);
                    placeRB.setNumber(i * 4 + 4);

                    coupe.setPlaceLT(placeLT);
                    coupe.setPlaceLB(placeLB);
                    coupe.setPlaceRT(placeRT);
                    coupe.setPlaceRB(placeRB);
                    wagon.addCoupes(coupe);
                }
            }
        });
    }

    public static Wagon GetWagon() {
        Realm realm = Realm.getDefaultInstance();
        Wagon wagon;
        try {
            wagon = realm.copyFromRealm(realm.where(Wagon.class).findFirst());
        } catch (Exception e) {
            realm.close();
            return null;
        }
        realm.close();
        return wagon;
    }
}
