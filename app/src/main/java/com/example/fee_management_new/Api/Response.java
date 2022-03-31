package com.example.fee_management_new.Api;

import java.util.ArrayList;

public class Response {
    public ArrayList<Item2> items;
    public Meta meta;
    public ArrayList<Standard> standards;

    public ArrayList<Item2> getItems() {
        return items;
    }

    public Meta getMeta() {
        return meta;
    }

    public ArrayList<Standard> getStandards() {
        return standards;
    }
}
