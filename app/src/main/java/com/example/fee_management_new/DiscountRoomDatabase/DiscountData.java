package com.example.fee_management_new.DiscountRoomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "discount_table")
public class DiscountData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "discountdetail")
    private String discountdetail;

    @ColumnInfo(name = "discountAmount")
    private String discountAmount;



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDiscountdetail() {
        return discountdetail;
    }

    public void setDiscountdetail(String discountdetail) {
        this.discountdetail = discountdetail;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }
}

