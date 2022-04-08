package com.example.fee_management_new.DiscountRoomDatabase;
import static androidx.room.OnConflictStrategy.REPLACE;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface MainDao {
    @Insert(onConflict = REPLACE)
    void insert(DiscountData discountData);

    @Query("SELECT * From discount_table")
    List<DiscountData> getAlldiscountdata();

   @Query("DELETE From discount_table")
   void deleteAll();

   @Query("SELECT count (*) From discount_table")
    int getcount();
}
