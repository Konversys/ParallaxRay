package ru.konverdev.parallax.model.classes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Coupe extends RealmObject {
    public static final int COUPE = 1;
    public static final int RESERVED_SEAT = 2;

    @PrimaryKey
    String id;
    int number;
    Place placeLT;
    Place placeRT;
    Place placeLB;
    Place placeRB;
    Place placeST;
    Place placeSB;
    int type;

    public Coupe() {
    }

    public Coupe(int number, Place placeLT, Place placeRT, Place placeLB, Place placeRB) {
        this.number = number;
        this.placeLT = placeLT;
        this.placeRT = placeRT;
        this.placeLB = placeLB;
        this.placeRB = placeRB;
        type = COUPE;
    }

    public Coupe(int number, Place placeLT, Place placeRT, Place placeLB, Place placeRB, Place placeST, Place placeSB) {
        this.number = number;
        this.placeLT = placeLT;
        this.placeRT = placeRT;
        this.placeLB = placeLB;
        this.placeRB = placeRB;
        this.placeST = placeST;
        this.placeSB = placeSB;
        type = RESERVED_SEAT;
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

    public Place getPlaceLT() {
        return placeLT;
    }

    public void setPlaceLT(Place placeLT) {
        this.placeLT = placeLT;
    }

    public Place getPlaceRT() {
        return placeRT;
    }

    public void setPlaceRT(Place placeRT) {
        this.placeRT = placeRT;
    }

    public Place getPlaceLB() {
        return placeLB;
    }

    public void setPlaceLB(Place placeLB) {
        this.placeLB = placeLB;
    }

    public Place getPlaceRB() {
        return placeRB;
    }

    public void setPlaceRB(Place placeRB) {
        this.placeRB = placeRB;
    }

    public Place getPlaceST() {
        return placeST;
    }

    public void setPlaceST(Place placeST) {
        this.placeST = placeST;
    }

    public Place getPlaceSB() {
        return placeSB;
    }

    public void setPlaceSB(Place placeSB) {
        this.placeSB = placeSB;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
