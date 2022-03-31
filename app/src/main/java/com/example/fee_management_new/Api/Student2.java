package com.example.fee_management_new.Api;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class Student2 {
    public int id;
    public int rollNo;
    public Object studentDetails;
    public int standardId;
    public int userId;

    public int getId() {
        return id;
    }

    public int getRollNo() {
        return rollNo;
    }

    public Object getStudentDetails() {
        return studentDetails;
    }

    public int getStandardId() {
        return standardId;
    }

    public int getUserId() {
        return userId;
    }
}
