package com.example.fee_management_new.Api;

public class UpdateOfflineTransactionRequest {
    int transactionId;
    String paymentMethod,chequeNo,paymentDate;

    public UpdateOfflineTransactionRequest(int transactionId, String paymentMethod, String chequeNo, String paymentDate) {
        this.transactionId = transactionId;
        this.paymentMethod = paymentMethod;
        this.chequeNo = chequeNo;
        this.paymentDate = paymentDate;
    }
}
