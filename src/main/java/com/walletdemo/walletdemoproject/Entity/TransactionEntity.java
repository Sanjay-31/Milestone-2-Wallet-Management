package com.walletdemo.walletdemoproject.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TransactionData")
public class TransactionEntity<transaction_idpublic> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private long transaction_id;

//    @Column(name = "from_account")
//    private String  fromaccount;
//    @Column(name = "to_account")
//    private String toaccount;
    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonIgnore
    private WalletEntity from;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="receiver_id")
    private WalletEntity to;

    public TransactionEntity()
    {

    }

    public TransactionEntity(long transaction_id, WalletEntity fromaccount, WalletEntity toaccount, Double amount) {
        this.transaction_id = transaction_id;
        this.from = fromaccount;
        this.to = toaccount;
        this.amount = amount;
    }

    public void setTransaction_id(long transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setFrom(WalletEntity from) {
        this.from = from;
    }

    public void setTo(WalletEntity to) {
        this.to = to;
    }

    public long getTransaction_id() {
        return transaction_id;
    }

    public Double getAmount() {
        return amount;
    }

    public WalletEntity getFrom() {
        return from;
    }

    public WalletEntity getTo() {
        return to;
    }

    //    public Double getAmount() {
//        return amount;
//    }
//
//    public long getTransaction_id() {
//        return transaction_id;
//    }

//    public WalletEntity getFromaccount() {
//        return from;
//    }
//
//    public WalletEntity getToaccount() {
//        return to;
//    }
}
