package com.example.fee_management_new.Modalclass;

public class DiscoutDetail {
    String name;
    String amount;
    String details;

    public DiscoutDetail(String name, String amount, String details) {
        this.name = name;
        this.amount = amount;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getDetails() {
        return details;
    }
}
