package com.example.fee_management_new.Api;

import java.util.ArrayList;

public class RecentTransactions {
    public ArrayList<AllTransactionList> allTransactionList;
    public ArrayList<Standard> standards;
    public int count;

    public ArrayList<AllTransactionList> getAllTransactionList() {
        return allTransactionList;
    }

    public ArrayList<Standard> getStandards() {
        return standards;
    }

    public int getCount() {
        return count;
    }
}
