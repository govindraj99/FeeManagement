package com.example.fee_management_new.Modalclass;

public class RecentActivitiesone {
    String name,std,section,amount,date,status,note,image;
    int id;

    public RecentActivitiesone(String name, String std, String section, String amount, String date, String status,String note,String image,int id) {
        this.name = name;
        this.std = std;
        this.section = section;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.note = note;
        this.image = image;
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public String getName() {
        return name;
    }

    public String getStd() {
        return std;
    }

    public String getSection() {
        return section;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }
}
