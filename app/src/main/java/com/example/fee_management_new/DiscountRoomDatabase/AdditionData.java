package com.example.fee_management_new.DiscountRoomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "addition_table")
public class AdditionData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "addtionDetail")
    private String additionDetail;

    @ColumnInfo(name = "additionAmount")
    private String additionAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdditionDetail() {
        return additionDetail;
    }

    public void setAdditionDetail(String additionDetail) {
        this.additionDetail = additionDetail;
    }

    public String getAdditionAmount() {
        return additionAmount;
    }

    public void setAdditionAmount(String additionAmount) {
        this.additionAmount = additionAmount;
    }
}
