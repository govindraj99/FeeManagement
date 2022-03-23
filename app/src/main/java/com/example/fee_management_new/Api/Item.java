package com.example.fee_management_new.Api;

import java.util.Date;

public class Item {
    public int id;
    public String orgId;
    public int settlementId;
    public String status;
    public String refNo;
    public Object rejectReason;
    public String invoice;
    public String startDate;
    public String endDate;
    public String transactionId;
    public Date openedDate;
    public Date closedDate;
    public String total;
    public int noOftransactions;

    public int getId() {
        return id;
    }

    public String getOrgId() {
        return orgId;
    }

    public int getSettlementId() {
        return settlementId;
    }

    public String getStatus() {
        return status;
    }

    public String getRefNo() {
        return refNo;
    }

    public Object getRejectReason() {
        return rejectReason;
    }

    public String getInvoice() {
        return invoice;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Date getOpenedDate() {
        return openedDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public String getTotal() {
        return total;
    }

    public int getNoOftransactions() {
        return noOftransactions;
    }
}
