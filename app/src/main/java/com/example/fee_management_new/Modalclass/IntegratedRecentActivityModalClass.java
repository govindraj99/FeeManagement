package com.example.fee_management_new.Modalclass;

public class IntegratedRecentActivityModalClass {
    String name,amount,date;
    int id;

    public IntegratedRecentActivityModalClass(String name, String amount, String date,int id) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

//    public String getMonth() {
//        return month;
//    }

    public String getDate() {
        return date;
    }
}
