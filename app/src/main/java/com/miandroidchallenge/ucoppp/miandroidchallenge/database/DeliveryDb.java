package com.miandroidchallenge.ucoppp.miandroidchallenge.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity(tableName = "delivery")
public class DeliveryDb {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "delivery_id")
    private String delivery_id;
    @NonNull
    @ColumnInfo(name = "description")
    private String description;
    @NonNull
    @ColumnInfo(name = "imageUrl")
    private String imageUrl;
    @ColumnInfo(name = "latitude")
    private Double latitude;
    @ColumnInfo(name = "longitude")
    private Double longitude;
    @ColumnInfo(name = "address")
    private String address;

    public DeliveryDb(@NonNull  String delivery_id, @NonNull String description, @NonNull String imageUrl, Double latitude, Double longitude, String address) {
        this.delivery_id = delivery_id;
        this.description = description;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }


    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
