package com.walletdemo.walletdemoproject.Entity;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "WalletData")
public class WalletEntity {

    @Id
    @Column(name = "phone_number")
    private String phoneNumber;
    private  Double accountBalance;
    private String status="Active";


    public WalletEntity()
    {

    }
    public Double getAccountBalance() {
        return accountBalance;
    }

    public String  getPhoneNumber() {
        return phoneNumber;
    }

    public void setAccountBalance(Double accountBalance) {
       this. accountBalance = accountBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
