package com.example.fee_management_new.DiscountRoomDatabase;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface MainDaoTwo {
    @Insert(onConflict = REPLACE)
    void insert(AdditionData additionData);

    @Query("SELECT * FROM addition_table")
    List<AdditionData> getAlladditiondata();

    @Query("DELETE  From addition_table")
    void deleteAlldata();
}
