package com.example.fee_management_new.Api;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class Show {
    public String type;
    public String message;

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
