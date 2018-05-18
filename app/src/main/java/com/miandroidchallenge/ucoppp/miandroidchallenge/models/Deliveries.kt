package com.miandroidchallenge.ucoppp.miandroidchallenge.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Deliveries(
        @SerializedName("description")
        @Expose
        var description: String? = null,
        @SerializedName("imageUrl")
        @Expose
        var imageUrl: String? = null,
        @SerializedName("location")
        @Expose
        var location: Location? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Location::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeString(imageUrl)
        parcel.writeParcelable(location, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Deliveries> {
        override fun createFromParcel(parcel: Parcel): Deliveries {
            return Deliveries(parcel)
        }

        override fun newArray(size: Int): Array<Deliveries?> {
            return arrayOfNulls(size)
        }
    }
}