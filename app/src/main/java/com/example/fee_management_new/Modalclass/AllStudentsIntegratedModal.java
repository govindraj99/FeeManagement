package com.example.fee_management_new.Modalclass;

public class AllStudentsIntegratedModal {
    String name,rollno,transaction,image;

    public AllStudentsIntegratedModal(String name, String rollno, String transaction,String image) {
        this.name = name;
        this.rollno = rollno;
        this.transaction = transaction;
        this.image = image;
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
