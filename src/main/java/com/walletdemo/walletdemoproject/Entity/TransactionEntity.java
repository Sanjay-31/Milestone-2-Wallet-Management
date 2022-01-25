package com.walletdemo.walletdemoproject.Entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "TransactionData")
public class TransactionEntity {
//
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private long transaction_id;

    @Column(name = "from_account")
    private String  fromaccount;
    @Column(name = "to_account")
    private String toaccount;
    @Column(name = "amount")
    private Double amount;
    public TransactionEntity()
    {

    }

    public Double getAmount() {
        return amount;
    }

    public long getTransaction_id() {
        return transaction_id;
    }

    public String getFromaccount() {
        return fromaccount;
    }

    public String getToaccount() {
        return toaccount;
    }
}
