package ru.konverdev.parallax.model;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject {
    @PrimaryKey
    private String id;
    @Json(name = "title")
    private String title;
    @Json(name = "category")
    private String category;
    @Json(name = "count")
    private String count;
    @Json(name = "about")
    private String about;
    @Json(name = "price")
    private double price;
    private boolean sale;
    private int total;
    private int sold;
    @Ignore
    private boolean swipe;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public boolean isSwipe() {
        return swipe;
    }

    public void setSwipe(boolean swipe) {
        this.swipe = swipe;
    }

    public static void SaveProducts(final List<Product> products, boolean isSale, boolean truncate) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (truncate) {
                    realm.where(Product.class).equalTo("sale", isSale).findAll().deleteAllFromRealm();
                }
                realm.copyToRealm(products.stream().filter(x -> isSale).collect(Collectors.toList()));
            }
        });
        realm.close();
    }

    public static void AddProduct(Product product) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(product);
            }
        });
        realm.close();
    }

    public static void DeleteProduct(Product product) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Product.class).equalTo("id", product.getId()).findFirst().deleteFromRealm();
            }
        });
        realm.close();
    }

    public static void UpdateSellProduct(Product product) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(product);
            }
        });
        realm.close();
    }

    public static void CleanProducts(boolean isSale) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Product.class).equalTo("sale", isSale).findAll().deleteAllFromRealm();
            }
        });
        realm.close();
    }

    public static ArrayList<Product> GetProducts(boolean isSale) {
        ArrayList<Product> products;
        Realm realm = Realm.getDefaultInstance();
        products = new ArrayList<>(realm.copyFromRealm(realm.where(Product.class).equalTo("sale", isSale).findAll()));
        realm.close();
        return products;
    }

    public static double SumSoldProducts() {
        double sum = 0;
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Product> results = realm.where(Product.class).equalTo("sale", true).findAll();
        for (Product item : results) {
            sum += item.getPrice() * item.getSold();
        }
        realm.close();
        return sum;
    }

    public static double SumTotalProducts() {
        double sum = 0;
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Product> results = realm.where(Product.class).equalTo("sale", true).findAll();
        for (Product item : results) {
            sum += item.getPrice() * item.getTotal();
        }
        return sum;
    }
}
