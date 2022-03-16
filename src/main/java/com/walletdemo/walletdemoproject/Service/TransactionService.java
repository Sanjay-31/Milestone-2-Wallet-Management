package com.walletdemo.walletdemoproject.Service;

import com.walletdemo.walletdemoproject.Controller.TransactionController;
import com.walletdemo.walletdemoproject.Model.TransactionData;
import com.walletdemo.walletdemoproject.DTO.TransactionSummaryData;
import com.walletdemo.walletdemoproject.Model.WalletData;
import com.walletdemo.walletdemoproject.Repository.TransactionRepo;
import com.walletdemo.walletdemoproject.Repository.WalletRepo;
import com.walletdemo.walletdemoproject.ResponseClass.DateResponse;
import com.walletdemo.walletdemoproject.ResponseClass.TransactionResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransactionService {

    Logger logger= LogManager.getLogger(TransactionController.class);

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    WalletRepo walletRepo;

    @Autowired
    WalletService walletService;





    public List<TransactionData> get() {
        return transactionRepo.findAll();
    }

    public TransactionData createtransaction(TransactionData t) {
        DateResponse dateResponse=new DateResponse();
        String date=dateResponse.getCurrentDateTime();
        t.setDate(date);
        double amount= t.getAmount();
        WalletData sender=walletRepo.findById(t.getSender()).get();
        WalletData receiver=walletRepo.findById(t.getReceiver()).get();

        sender.setAccountBalance(sender.getAccountBalance()-amount);
        receiver.setAccountBalance(receiver.getAccountBalance()+amount);
        walletRepo.save(sender);
        walletRepo.save(receiver);
        return transactionRepo.save(t);
    }

    public List<TransactionSummaryData> getData(String phoneNumber) {
        List<TransactionData>transaction=transactionRepo.findAll();
        ArrayList<TransactionSummaryData>data = new ArrayList<TransactionSummaryData>();
        for(TransactionData t:transaction)
        {
            if(t.getSender().equals(phoneNumber))
            {

            data.add(new TransactionSummaryData("Sent To "+t.getReceiver(),t.getAmount(),t.getDate()));
            }
            else if(t.getReceiver().equals(phoneNumber))
            {
            data.add(new TransactionSummaryData("Received from "+t.getSender(),t.getAmount(),t.getDate()));
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
