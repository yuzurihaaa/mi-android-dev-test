package com.miandroidchallenge.ucoppp.miandroidchallenge.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveriesModel implements Parcelable {

    @SerializedName("description")
    @Expose
    private String description = "";
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl = "";

    public DeliveriesModel() {
    }

    public DeliveriesModel(String description, String imageUrl, LocationModel location) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.location = location;
    }

    protected DeliveriesModel(Parcel in) {
        description = in.readString();
        imageUrl = in.readString();
        location = in.readParcelable(LocationModel.class.getClassLoader());
    }

    public static final Creator<DeliveriesModel> CREATOR = new Creator<DeliveriesModel>() {
        @Override
        public DeliveriesModel createFromParcel(Parcel in) {
            return new DeliveriesModel(in);
        }

        @Override
        public DeliveriesModel[] newArray(int size) {
            return new DeliveriesModel[size];
        }
    };

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    @SerializedName("location")
    @Expose
    private LocationModel location = null;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeParcelable(location, flags);
    }
}
