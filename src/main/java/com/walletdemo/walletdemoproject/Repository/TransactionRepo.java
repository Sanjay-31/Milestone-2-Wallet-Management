package com.walletdemo.walletdemoproject.Repository;

import com.walletdemo.walletdemoproject.Model.TransactionData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<TransactionData,Long> {
}
