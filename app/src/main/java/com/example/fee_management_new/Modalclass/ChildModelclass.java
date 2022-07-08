package com.example.fee_management_new.Modalclass;

public class ChildModelclass {
    String Coursename,section,StudentCount,std;
    int id;

    public ChildModelclass(String section, String studentCount,int id,String std) {
//        Coursename = coursename;
        this.section = section;
        StudentCount = studentCount;
        this.id=id;
        this.std = std;
    }

    public String getStd() {
        return std;
    }

//    public String getCoursename() {
//        return Coursename;
//    }

    public String getSection() {
        return section;
    }

    public String getStudentCount() {
        return StudentCount;
    }

    public int getId() {
        return id;
    }
}
