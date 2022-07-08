package com.example.fee_management_new.Modalclass;

public class IntegratedRecentActivityModalClass {
    String name,amount,date,image,status;
    int id;

    public IntegratedRecentActivityModalClass(String name, String amount, String date,String image,int id,String status) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.image = image;
        this.id = id;
        this.status = status;

    }

    public String getStatus() {
        return status;
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

    public String getImage() {
        return image;
    }
}
