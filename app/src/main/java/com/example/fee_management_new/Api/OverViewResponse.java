package com.example.fee_management_new.Api;

import java.util.ArrayList;
import java.util.Date;

public class OverViewResponse {
    public TotalPaymentPending totalPaymentPending;
    public TotalPaymentPaid totalPaymentPaid;
    public TotalPaymentRefunded totalPaymentRefunded;
    public TotalPaymentOverDue totalPaymentOverDue;
    public TotalPaymentCancelled totalPaymentCancelled;
    public TotalTransactionRequested totalTransactionRequested;
    public ArrayList<Graph> graph;
    public RecentTransactions recentTransactions;
    public SettlementLists settlementLists;
    public ArrayList<Object> paidTransactions;
    public ArrayList<Object> upcomingTransactions;
    public String feepayer;
    public boolean gst;

    public TotalPaymentPending getTotalPaymentPending() {
        return totalPaymentPending;
    }

    public TotalPaymentPaid getTotalPaymentPaid() {
        return totalPaymentPaid;
    }

    public TotalPaymentRefunded getTotalPaymentRefunded() {
        return totalPaymentRefunded;
    }

    public TotalPaymentOverDue getTotalPaymentOverDue() {
        return totalPaymentOverDue;
    }

    public TotalPaymentCancelled getTotalPaymentCancelled() {
        return totalPaymentCancelled;
    }

    public TotalTransactionRequested getTotalTransactionRequested() {
        return totalTransactionRequested;
    }

    public ArrayList<Graph> getGraph() {
        return graph;
    }

    public RecentTransactions getRecentTransactions() {
        return recentTransactions;
    }

    public SettlementLists getSettlementLists() {
        return settlementLists;
    }

    public ArrayList<Object> getPaidTransactions() {
        return paidTransactions;
    }

    public ArrayList<Object> getUpcomingTransactions() {
        return upcomingTransactions;
    }

    public String getFeepayer() {
        return feepayer;
    }

    public boolean isGst() {
        return gst;
    }
}


