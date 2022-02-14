package com.walletdemo.walletdemoproject.Repository;


import com.walletdemo.walletdemoproject.Model.WalletData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<WalletData, String> {
    public WalletData getByPhoneNumber(String s);
}
