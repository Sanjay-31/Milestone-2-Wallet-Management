package com.walletdemo.walletdemoproject.Entity;

import lombok.*;
import javax.persistence.*;

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
    private String password;
    private String date;


    public WalletEntity()
    {

    }
    public Double getAccountBalance() {
        return accountBalance;
    }

    public String getPassword() {
        return password;
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


    public void setDate(String date) {
        this.date = date;
    }
}
