package com.example.fee_management_new.Api;

import java.util.ArrayList;
import java.util.Date;

public class Standard2 {
    public int id;
    public String std;
    public String section;
    public String stream;
    public Date createdAt;
    public Date updatedAt;
    public ArrayList<String> students;
    public String status;
    public int courseId;
    public String courseName;

    public int getId() {
        return id;
    }

    public String getStd() {
        return std;
    }

    public String getSection() {
        return section;
    }

    public String getStream() {
        return stream;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public String getStatus() {
        return status;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }
}
