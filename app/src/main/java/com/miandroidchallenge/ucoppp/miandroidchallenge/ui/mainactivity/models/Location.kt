package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.models

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
)