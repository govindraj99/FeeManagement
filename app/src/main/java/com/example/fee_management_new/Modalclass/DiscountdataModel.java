package com.example.fee_management_new.Modalclass;

public class DiscountdataModel {
    String discountDetail,discountAmount,discountDescription;

    public DiscountdataModel(String discountDetail, String discountAmount, String discountDescription) {
        this.discountDetail = discountDetail;
        this.discountAmount = discountAmount;
        this.discountDescription = discountDescription;
    }

    public String getDiscountDetail() {
        return discountDetail;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }
}
