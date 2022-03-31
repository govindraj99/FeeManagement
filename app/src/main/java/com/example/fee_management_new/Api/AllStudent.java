package com.example.fee_management_new.Api;

public class AllStudent {
    public int rollNo;
    public int standardId;
    public int id;
    public String name;
    public String image;
    public int count;

    public AllStudent(int rollNo, int standardId, int id, String name, String image, int count) {
        this.rollNo = rollNo;
        this.standardId = standardId;
        this.id = id;
        this.name = name;
        this.image = image;
        this.count = count;
    }

    public int getRollNo() {
        return rollNo;
    }

    public int getStandardId() {
        return standardId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getCount() {
        return count;
    }
}