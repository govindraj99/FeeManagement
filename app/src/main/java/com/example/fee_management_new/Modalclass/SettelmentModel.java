package com.example.fee_management_new.Modalclass;

public class SettelmentModel {

    String amount,date,time,refId;

    public SettelmentModel(String amount, String date, String time, String refId) {
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.refId = refId;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getRefId() {
        return refId;
    }
}
