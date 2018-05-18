package com.miandroidchallenge.ucoppp.miandroidchallenge.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import dagger.Module

@Module
@Database(entities = [(DeliveriesDb::class)], version = 1)
abstract class DeliveriesDatabase : RoomDatabase() {

    abstract fun DeliveriesDao(): DeliveriesDao

    companion object {


        @Volatile
        private var INSTANCE: DeliveriesDatabase? = null

        fun getInstance(context: Context): DeliveriesDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context): DeliveriesDatabase =
                Room.databaseBuilder(context.applicationContext,
                        DeliveriesDatabase::class.java, "Users")
                        .fallbackToDestructiveMigration()
                        .build()
    }

}