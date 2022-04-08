package com.example.fee_management_new.Api;

import java.util.ArrayList;

public class RequestGeneratePaymentRequestIndividual {
    public int amount;
    public String note;
    public String paymentType;
    public String paymentMethod;
    public String discountDetails;
    public Object additionDetails;
    public String dueDate;
    public ArrayList<Integer> userIds;
    public int standardId;

    public RequestGeneratePaymentRequestIndividual(int amount, String note, String paymentType, String paymentMethod, String discountDetails, Object additionDetails, String dueDate, ArrayList<Integer> userIds, int standardId) {
        this.amount = amount;
        this.note = note;
        this.paymentType = paymentType;
        this.paymentMethod = paymentMethod;
        this.discountDetails = discountDetails;
        this.additionDetails = additionDetails;
        this.dueDate = dueDate;
        this.userIds = userIds;
        this.standardId = standardId;
    }

    public int getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getDiscountDetails() {
        return discountDetails;
    }

    public Object getAdditionDetails() {
        return additionDetails;
    }

    public String getDueDate() {
        return dueDate;
    }

    public ArrayList<Integer> getUserIds() {
        return userIds;
    }

    public int getStandardId() {
        return standardId;
    }
}
