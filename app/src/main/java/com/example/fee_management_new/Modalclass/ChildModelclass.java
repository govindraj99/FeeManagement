package com.example.fee_management_new.Modalclass;

public class ChildModelclass {
    String Coursename,section,StudentCount;

    public ChildModelclass(String coursename, String section, String studentCount) {
        Coursename = coursename;
        this.section = section;
        StudentCount = studentCount;
    }

    public String getCoursename() {
        return Coursename;
    }

    public String getSection() {
        return section;
    }

    public String getStudentCount() {
        return StudentCount;
    }
}
