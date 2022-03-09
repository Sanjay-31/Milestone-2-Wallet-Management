package com.walletdemo.walletdemoproject.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TransactionSummaryData {
    private Double transaction_amount;
    //for debit and credit
    private String message;
    private String date;

    public Double getTransaction_amount() {
        return transaction_amount;
    }
    public void setTransaction_amount(Double transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TransactionSummaryData(String message, Double transaction_amount, String date)
    {
        this.transaction_amount=transaction_amount;
        this.message=message;
        this.date=date;
    }
}
