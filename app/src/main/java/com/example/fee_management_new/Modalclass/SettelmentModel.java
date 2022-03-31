package com.example.fee_management_new.Modalclass;

public class SettelmentModel {

    String amount,date,refId;

    public SettelmentModel(String amount, String date, String refId) {
        this.amount = amount;
        this.date = date;

        this.refId = refId;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }



    public String getRefId() {
        return refId;
    }
}
