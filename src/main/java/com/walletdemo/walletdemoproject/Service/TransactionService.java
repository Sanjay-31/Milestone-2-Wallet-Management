package com.walletdemo.walletdemoproject.Service;

import com.walletdemo.walletdemoproject.Model.TransactionData;
import com.walletdemo.walletdemoproject.Model.TransactionResponseData;
import com.walletdemo.walletdemoproject.Model.WalletData;
import com.walletdemo.walletdemoproject.Repository.TransactionRepo;
import com.walletdemo.walletdemoproject.Repository.WalletRepo;
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


    public List<TransactionData> get() {
        return transactionRepo.findAll();
    }

    public TransactionData createtransaction(TransactionData t) {

       double amount= t.getAmount();
        WalletData sender=walletRepo.findById(t.getSender()).get();
        WalletData receiver=walletRepo.findById(t.getReceiver()).get();

        sender.setAccountBalance(sender.getAccountBalance()-amount);
        receiver.setAccountBalance(receiver.getAccountBalance()+amount);
        return transactionRepo.save(t);
    }

    public List<TransactionResponseData> getData(String phoneNumber) {
        List<TransactionData>transaction=transactionRepo.findAll();
        ArrayList<TransactionResponseData>data = new ArrayList<TransactionResponseData>();
        for(TransactionData t:transaction)
        {
            if(t.getSender().equals(phoneNumber))
            {

            data.add(new TransactionResponseData("Sent To "+t.getReceiver(),t.getAmount(),t.getDate()));
            }
            else if(t.getReceiver().equals(phoneNumber))
            {
            data.add(new TransactionResponseData("Received from "+t.getSender(),t.getAmount(),t.getDate()));
            }
        }
        return data;
    }
    public TransactionData getById(long id)
    {
        return transactionRepo.findById(id).get();
    }

    public boolean chekTransctionById(long transaction_id) {
        return transactionRepo.existsById(transaction_id);
    }
}
