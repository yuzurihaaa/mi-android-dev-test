package com.miandroidchallenge.ucoppp.miandroidchallenge.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import dagger.Module;
import kotlin.jvm.Volatile;

@Module
@Database(entities = {DeliveryDb.class}, version = 1)
public abstract class DeliveryDatabase extends RoomDatabase {

    public abstract DeliveryDao deliveryDao();

    @Volatile
    private static DeliveryDatabase INSTANCE = null;

    public static DeliveryDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static DeliveryDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                DeliveryDatabase.class, "delivery")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

}
