package com.walletdemo.walletdemoproject.Model;


import com.walletdemo.walletdemoproject.Strategy.DataEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "TransactionData")
public class TransactionData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private long transaction_id;

    @Column(name = "sender")
    private String  sender;
    @Column(name = "receiver")
    private String receiver;
    @Column(name = "amount")
    private Double amount;
    private String date;

    public TransactionData()
    {

    }

    public TransactionData(long transaction_id, String fromaccount, String toaccount, Double amount) {
        this.transaction_id = transaction_id;
        this.sender = fromaccount;
        this.receiver= toaccount;
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public long getTransaction_id() {
        return transaction_id;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getDate() {
        return date;
    }
}
