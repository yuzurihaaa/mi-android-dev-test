package com.miandroidchallenge.ucoppp.miandroidchallenge.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Location(
        @SerializedName("lat")
        @Expose
        var lat: Double? = null,
        @SerializedName("lng")
        @Expose
        var lng: Double? = null,
        @SerializedName("address")
        @Expose
        var address: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(lat)
        parcel.writeValue(lng)
        parcel.writeString(address)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location {
            return Location(parcel)
        }

        override fun newArray(size: Int): Array<Location?> {
            return arrayOfNulls(size)
        }
    }
}