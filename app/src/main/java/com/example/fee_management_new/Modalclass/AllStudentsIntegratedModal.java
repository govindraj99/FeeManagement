package com.example.fee_management_new.Modalclass;

public class AllStudentsIntegratedModal {
    String name,rollno,transaction,image;
    int userId;

    public AllStudentsIntegratedModal(String name, String rollno, String transaction,String image,int userId) {
        this.name = name;
        this.rollno = rollno;
        this.transaction = transaction;
        this.image = image;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getRollno() {
        return rollno;
    }

    public String getTransaction() {
        return transaction;
    }

    public String getImage() {
        return image;
    }
}
