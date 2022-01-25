package com.walletdemo.walletdemoproject.Repository;

import com.walletdemo.walletdemoproject.Entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<TransactionEntity,Long> {
}
