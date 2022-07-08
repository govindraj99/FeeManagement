package com.example.fee_management_new.Modalclass;

public class AdditionDataModalClass {
    String additionTitle,additionAmount,additionDescription;

    public AdditionDataModalClass(String additionTitle, String additionAmount, String additionDescription) {
        this.additionTitle = additionTitle;
        this.additionAmount = additionAmount;
        this.additionDescription = additionDescription;
    }

    public String getAdditionTitle() {
        return additionTitle;
    }

    public String getAdditionAmount() {
        return additionAmount;
    }

    public String getAdditionDescription() {
        return additionDescription;
    }
}
