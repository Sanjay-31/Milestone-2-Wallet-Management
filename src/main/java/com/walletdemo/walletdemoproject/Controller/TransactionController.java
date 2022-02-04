package com.walletdemo.walletdemoproject.Controller;


import com.walletdemo.walletdemoproject.Entity.TransactionEntity;
import com.walletdemo.walletdemoproject.Repository.TransactionRepo;
import com.walletdemo.walletdemoproject.Entity.TransactionData;
import com.walletdemo.walletdemoproject.ResponseClass.TransactionResponse;
import com.walletdemo.walletdemoproject.Service.TransactionService;
import com.walletdemo.walletdemoproject.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    WalletService walletService;

    @Autowired
    private TransactionRepo transactionRepo;

    TransactionResponse transactionResponse=new TransactionResponse();


    @GetMapping("/transaction")
    public List<TransactionEntity> getAllTransaction() {
        return transactionService.get();
    }

    @GetMapping("/transaction/{phoneNumber}")
    public ResponseEntity<Object> getTransaction(@PathVariable String phoneNumber)
    {

         List<TransactionData> t=transactionService.getData(phoneNumber);
         if(walletService.checkUserExist(phoneNumber))
         {
             return transactionResponse.getResponse(t,"Retrieval of Data is successful");
         }
        return transactionResponse.getResponse(t,"Unsuccessful, User does not exist");
    }

    @PostMapping("/transaction")
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionEntity t) {

        if(walletService.checkUserExist(t.getFromaccount())&&walletService.checkUserExist(t.getToaccount()))
        {
            if(walletService.checkforcurrentinactive(t.getToaccount(),t.getFromaccount()))
            {
                return transactionResponse.getPostResponse(t,"Transaction Unsuccessful,User is not Active");
            }
            else if(walletService.sufficientbalance(t.getFromaccount(),t.getAmount()))
            {
                return transactionResponse.getPostResponse(t,"Transaction Unsuccessful,Insufficient Balance");
            }
           transactionService.createtransaction(t);
           return transactionResponse.getPostResponse(t,"Transaction was Successful");
        }
        else  return transactionResponse.getPostResponse(t,"Transaction Unsuccessful,User does not exist");
    }
}
