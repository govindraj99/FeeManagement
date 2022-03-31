package com.example.fee_management_new.Modalclass;

public class FilterClassBottomModel {
    String std,section;
    int id;

    public FilterClassBottomModel(String std, String section,int id) {
        this.std = std;
        this.section = section;
        this.id = id;
    }

    public String getStd() {
        return std;
    }

    public String getSection() {
        return section;
    }
}
