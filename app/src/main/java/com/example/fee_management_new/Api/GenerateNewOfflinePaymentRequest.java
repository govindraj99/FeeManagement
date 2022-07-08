package com.example.fee_management_new.Api;

import java.util.ArrayList;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class GenerateNewOfflinePaymentRequest {
    public double amount;
    public String note;
    public String paymentType;
    public String paymentMethod;
    public String discountDetails;
    public String additionDetails;
    public String dueDate;
    public ArrayList<Integer> userIds;
    public int standardId;

    public GenerateNewOfflinePaymentRequest(double amount, String note, String paymentType, String paymentMethod, String discountDetails, String additionDetails, String dueDate, ArrayList<Integer> userIds, int standardId) {
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
}


