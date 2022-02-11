package com.walletdemo.walletdemoproject.Service;

import com.walletdemo.walletdemoproject.Entity.TransactionEntity;
import com.walletdemo.walletdemoproject.Entity.WalletEntity;
import com.walletdemo.walletdemoproject.Repository.TransactionRepo;
import com.walletdemo.walletdemoproject.Repository.WalletRepo;
import com.walletdemo.walletdemoproject.Entity.TransactionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransactionService {


    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    WalletService walletService;

    @Autowired
    WalletRepo walletRepo;


    public List<TransactionEntity> get() {
        return transactionRepo.findAll();
    }

    public TransactionEntity createtransaction(String  from_account, String to_account,Double amount) {

//       double amount= t.getAmount();
//        WalletEntity fromwallet=walletRepo.findById(t.getFromaccount()).get();
//        WalletEntity towallet=walletRepo.findById(t.getToaccount()).get();
//         TransactionEntity t=new TransactionEntity();



        WalletEntity sender=walletRepo.getByPhoneNumber(from_account);
        WalletEntity receiver=walletRepo.getByPhoneNumber(to_account);
        sender.setAccountBalance(sender.getAccountBalance()-amount);
        receiver.setAccountBalance(receiver.getAccountBalance()+amount);
        TransactionEntity t=new TransactionEntity();
        t.setFrom(sender);
        t.setTo(receiver);
        t.setAmount(amount);
        return transactionRepo.save(t);
    }
//public void createtransaction(String from_account, String to_account, Double amount) {
//
//}

//    public List<TransactionData> getData(String phoneNumber) {
//        List<TransactionEntity>transaction=transactionRepo.findAll();
//        ArrayList<TransactionData>data = new ArrayList<TransactionData>();
//        for(TransactionEntity t:transaction)
//        {
//            if(t.getFromaccount().equals(phoneNumber))
//            {
//            data.add(new TransactionData("Sent To "+t.getToaccount(),t.getAmount()));
//            }
//            else if(t.getToaccount().equals(phoneNumber))
//            {
//            data.add(new TransactionData("Received from "+t.getFromaccount(),t.getAmount()));
//            }
//        }
//        return data;
//    }
    public TransactionEntity getById(long id)
    {
        return transactionRepo.findById(id).get();
    }

    public boolean chekTransctionById(long transaction_id) {
        return transactionRepo.existsById(transaction_id);
    }


}
