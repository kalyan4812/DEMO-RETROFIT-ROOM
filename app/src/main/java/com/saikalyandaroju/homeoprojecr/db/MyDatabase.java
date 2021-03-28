package com.saikalyandaroju.homeoprojecr.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CrewMember.class}, version = 3)
public abstract class MyDatabase extends RoomDatabase {
    public abstract MyDao myDao();

    private static MyDatabase instance;

   public static MyDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, MyDatabase.class, "Crew_db").fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}
