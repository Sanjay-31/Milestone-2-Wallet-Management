package com.walletdemo.walletdemoproject.Controller;


import com.walletdemo.walletdemoproject.Entity.TransactionEntity;
import com.walletdemo.walletdemoproject.Entity.WalletEntity;
import com.walletdemo.walletdemoproject.Repository.TransactionRepo;
import com.walletdemo.walletdemoproject.Entity.TransactionData;
import com.walletdemo.walletdemoproject.ResponseClass.TransactionResponse;
import com.walletdemo.walletdemoproject.Service.TransactionService;
import com.walletdemo.walletdemoproject.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<TransactionEntity>> getAllTransaction() {
        return new ResponseEntity<>(transactionService.get(), HttpStatus.OK);
    }

//    @GetMapping("/transaction/{phoneNumber}")
//    public ResponseEntity<Object> getTransaction(@PathVariable String phoneNumber)
//    {
//         List<TransactionData> t=transactionService.getData(phoneNumber);
//         if(walletService.checkUserExist(phoneNumber))
//         {
//             return transactionResponse.getResponse(t,"Retrieval of Data is successful");
//         }
//        return transactionResponse.getResponse(t,"Unsuccessful, User does not exist");
//    }
    @GetMapping("/transactionid/{transaction_id}")
    public ResponseEntity<?> getSingleTransaction(@PathVariable long transaction_id)
    {
        if(transactionService.chekTransctionById(transaction_id)){
            TransactionEntity t=transactionService.getById(transaction_id);
            return transactionResponse.getByIdResponse(t,"Data Fetching successful",HttpStatus.FOUND);
        }
        else
        {
            return transactionResponse.getByIdResponse(null,"No transaction exist with id :"+transaction_id,HttpStatus.NOT_FOUND);
//            return new ResponseEntity<>("No transaction exist with id :"+transaction_id,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/transaction")
    public ResponseEntity<Object> createTransaction(@RequestParam String from_account
                                                    ,@RequestParam String to_account,
                                                    @RequestParam Double amount) {

//        transactionService.createtransaction(from_account,to_account,amount);

        TransactionEntity t=transactionService.createtransaction(from_account,to_account,amount);
        return new ResponseEntity<>(t,HttpStatus.FOUND);

//        if(walletService.checkUserExist(t.getFromaccount())&&walletService.checkUserExist(t.getToaccount()))
//        {
//            if(walletService.checkforcurrentinactive(t.getToaccount(),t.getFromaccount()))
//            {
//                return transactionResponse.getPostResponse(t,"Transaction Unsuccessful,User is not Active",HttpStatus.NOT_FOUND);
//            }
//            else if(walletService.sufficientbalance(t.getFromaccount(),t.getAmount()))
//            {
//                return transactionResponse.getPostResponse(t,"Transaction Unsuccessful,Insufficient Balance",HttpStatus.BAD_REQUEST);
//            }
//           transactionService.createtransaction(t);
//           return transactionResponse.getPostResponse(t,"Transaction Successful",HttpStatus.CREATED);
//        }
//        else  return transactionResponse.getPostResponse(t,"Transaction Unsuccessful,User does not exist",HttpStatus.NOT_FOUND);
    }

}
