package com.walletdemo.walletdemoproject.Service;

import com.walletdemo.walletdemoproject.Entity.TransactionEntity;
import com.walletdemo.walletdemoproject.Entity.WalletEntity;
import com.walletdemo.walletdemoproject.Repository.TransactionRepo;
import com.walletdemo.walletdemoproject.Repository.WalletRepo;
import com.walletdemo.walletdemoproject.Entity.TransactionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransactionService {


    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    WalletRepo walletRepo;


    public List<TransactionEntity> get() {
        return transactionRepo.findAll();
    }

    public TransactionEntity createtransaction(TransactionEntity t) {

       double amount= t.getAmount();
        WalletEntity fromwallet=walletRepo.findById(t.getFromaccount()).get();
        WalletEntity towallet=walletRepo.findById(t.getToaccount()).get();

        fromwallet.setAccountBalance(fromwallet.getAccountBalance()-amount);
        towallet.setAccountBalance(towallet.getAccountBalance()+amount);
        return transactionRepo.save(t);
    }

    public List<TransactionData> getData(String phoneNumber) {
        List<TransactionEntity>transaction=transactionRepo.findAll();
        ArrayList<TransactionData>data = new ArrayList<TransactionData>();
        for(TransactionEntity t:transaction)
        {
            if(t.getFromaccount().equals(phoneNumber))
            {
            data.add(new TransactionData("Sent To "+t.getToaccount(),t.getAmount()));
            }
            else if(t.getToaccount().equals(phoneNumber))
            {
            data.add(new TransactionData("Received from "+t.getFromaccount(),t.getAmount()));
            }
        }
        return data;
    }
    public TransactionEntity getById(long id)
    {
        return transactionRepo.findById(id).get();
    }

    public boolean chekTransctionById(long transaction_id) {
        return transactionRepo.existsById(transaction_id);
    }
}
