package com.miandroidchallenge.ucoppp.miandroidchallenge.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import java.util.*

@Entity(tableName = "deliveries")
data class DeliveriesDb(
        @PrimaryKey
        @ColumnInfo(name = "delivery_id")
        val delivery_id: String = UUID.randomUUID().toString(),
        @NonNull
        @ColumnInfo(name = "description")
        val description: String?,
        @NonNull
        @ColumnInfo(name = "imageUrl")
        val imageUrl: String?,
        @ColumnInfo(name = "latitude")
        val latitude: Double?,
        @ColumnInfo(name = "longitude")
        val longitude: Double?,
        @ColumnInfo(name = "address")
        val address: String?
)