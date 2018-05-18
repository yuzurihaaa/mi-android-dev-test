package com.miandroidchallenge.ucoppp.miandroidchallenge.models

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
)