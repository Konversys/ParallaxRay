package ru.konverdev.parallax.model.classes;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Checksum extends RealmObject {
    @PrimaryKey
    String id;
    @Json(name = "table")
    private String table;
    @Json(name = "value")
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static void SaveChecksums(final List<Checksum> checksums) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Checksum.class).findAll().deleteAllFromRealm();
                realm.copyToRealm(checksums);
            }
        });
        realm.close();
    }

    public static void CleanChecksums() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Checksum.class).findAll().deleteAllFromRealm();
            }
        });
        realm.close();
    }

    public static ArrayList<Checksum> GetChecksums() {
        ArrayList<Checksum> checksums;
        Realm realm = Realm.getDefaultInstance();
        checksums = new ArrayList<>(realm.copyFromRealm(realm.where(Checksum.class).findAll()));
        realm.close();
        return checksums;
    }
}
