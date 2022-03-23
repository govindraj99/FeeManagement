package com.example.fee_management_new.Modalclass;

public class IntegratedRecentActivityModalClass {
    String name,amount,month,date;

    public IntegratedRecentActivityModalClass(String name, String amount, String month, String date) {
        this.name = name;
        this.amount = amount;
        this.month = month;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getMonth() {
        return month;
    }

    public String getDate() {
        return date;
    }
}
