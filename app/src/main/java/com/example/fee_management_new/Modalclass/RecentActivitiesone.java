package com.example.fee_management_new.Modalclass;

public class RecentActivitiesone {
    String name,std,section,amount,date,status,note;

    public RecentActivitiesone(String name, String std, String section, String amount, String date, String status,String note) {
        this.name = name;
        this.std = std;
        this.section = section;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.note = note;
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
}
