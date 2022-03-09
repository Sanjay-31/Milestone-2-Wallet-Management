package com.walletdemo.walletdemoproject.Controller;


import com.walletdemo.walletdemoproject.Model.TransactionData;
import com.walletdemo.walletdemoproject.Repository.TransactionRepo;
import com.walletdemo.walletdemoproject.DTO.TransactionSummaryData;
import com.walletdemo.walletdemoproject.ResponseClass.TransactionResponse;
import com.walletdemo.walletdemoproject.Service.TransactionService;
import com.walletdemo.walletdemoproject.Service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    Logger logger= LogManager.getLogger(TransactionController.class);
    @Autowired
    private TransactionService transactionService;

    @Autowired
    WalletService walletService;

    @Autowired
    private TransactionRepo transactionRepo;

    TransactionResponse transactionResponse=new TransactionResponse();

    @GetMapping("/transaction")
    public ResponseEntity<List<TransactionData>> getAllTransaction() {
        logger.debug("Fetching List Of transaction");
        return new ResponseEntity<>(transactionService.get(), HttpStatus.OK);
    }

    @GetMapping("/transaction/{phoneNumber}")
    public ResponseEntity<Object> getTransaction(@PathVariable String phoneNumber)
    {

         if(walletService.checkUserExist(phoneNumber))
         {
             List<TransactionSummaryData> t=transactionService.getData(phoneNumber);
             logger.debug("getting transaction of "+phoneNumber+" "+t);
             return transactionResponse.getResponse(t,"Retrieval of Data is successful");
         }
         logger.error("User Not exist with phone-number : "+phoneNumber);
        return transactionResponse.getMessageResponse("Error Message : ","Unsuccessful, User does not exist",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/transactionId/{transaction_id}")
    public ResponseEntity<Object> getSingleTransaction(@PathVariable long transaction_id)
    {
        if(transactionService.chekTransctionById(transaction_id)){
            TransactionData t=transactionService.getById(transaction_id);
            return transactionResponse.getByIdResponse(t,"Data Fetching successful",HttpStatus.FOUND);
        }
        logger.error("No Transaction exist whit id : "+transaction_id);
            return transactionResponse.getByIdResponse(null,"No transaction exist with id :"+transaction_id,HttpStatus.NOT_FOUND);
    }

    @PostMapping("/transaction")
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionData t) {
        if(walletService.CheckUserExistByTransactionEntity(t))
        {
            if(walletService.checkforcurrentinactive(t.getReceiver(),t.getSender()))
            {
                return transactionResponse.getPostResponse(t,"Transaction Unsuccessful,User is not Active",HttpStatus.NOT_FOUND);
            }
            else if(walletService.CheckSufficientBalance(t.getSender(),t.getAmount()))
            {
                logger.error("Insufficient Balance , id : "+t.getSender());
                return transactionResponse.getPostResponse(t,"Transaction Unsuccessful,Insufficient Balance",HttpStatus.BAD_REQUEST);
            }
                transactionService.createtransaction(t);
                logger.debug("Transaction successful : "+t.getSender()+" to "+t.getReceiver()+" amount : "+t.getAmount());
                return transactionResponse.getPostResponse(t,"Transaction Successful",HttpStatus.CREATED);
        }
         return transactionResponse.getPostResponse(t,"Transaction Unsuccessful,User does not exist",HttpStatus.NOT_FOUND);
    }
}
