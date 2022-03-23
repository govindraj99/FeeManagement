package com.example.fee_management_new.Api;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class TotalPaymentPending {
    public int count;
    public String todayCount;
    public String amount;

    public int getCount() {
        return count;
    }

    public String getTodayCount() {
        return todayCount;
    }

    public String getAmount() {
        return amount;
    }
}
