package com.example.fee_management_new.DiscountRoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AdditionData.class},version = 5,exportSchema = false)
public abstract class RoomDBTwo extends RoomDatabase {
    private static RoomDBTwo databasetwo;
    private static String DATABASE_NAME = "databasetwo";
    public synchronized static RoomDBTwo getInstance(Context context){
        if (databasetwo == null){
            databasetwo = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDBTwo.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return databasetwo;
    }
    public abstract MainDaoTwo mainDaotwo();



}
