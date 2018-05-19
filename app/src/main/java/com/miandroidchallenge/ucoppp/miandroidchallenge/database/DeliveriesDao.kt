package com.miandroidchallenge.ucoppp.miandroidchallenge.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface DeliveriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDeliveries(vararg deliveries: DeliveriesDb)

    @Query("SELECT * FROM deliveries")
    fun selectAll(): MutableList<DeliveriesDb>
}