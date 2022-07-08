package com.example.fee_management_new.Modalclass;

public class AllActivitesTwoModal {
    String name,rollno,amount,note,date,status,image;
    int id;

    public AllActivitesTwoModal(String name, String rollno, String amount, String note, String date, String status,int id,String image) {
        this.name = name;
        this.rollno = rollno;
        this.amount = amount;
        this.note = note;
        this.date = date;
        this.status = status;
        this.id = id;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRollno() {
        return rollno;
    }

    public String getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
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
}
