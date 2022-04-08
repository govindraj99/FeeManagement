package com.example.fee_management_new.Modalclass;

public class TransactionData {
    String name,std,section,amount,date,note,paymenttype;

    public TransactionData(String name, String std, String section, String amount, String date,String note,String paymenttype) {
        this.name = name;
        this.std = std;
        this.section = section;
        this.amount = amount;
        this.date = date;
        this.note = note;
        this.paymenttype = paymenttype;

    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public String getNote() {
        return note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String setion) {
        this.section = setion;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
