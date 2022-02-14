package com.walletdemo.walletdemoproject.Controller;


import com.walletdemo.walletdemoproject.Model.TransactionData;
import com.walletdemo.walletdemoproject.Repository.TransactionRepo;
import com.walletdemo.walletdemoproject.Model.TransactionResponseData;
import com.walletdemo.walletdemoproject.ResponseClass.DateResponse;
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
        return new ResponseEntity<>(transactionService.get(), HttpStatus.OK);
    }

    @GetMapping("/transaction/{phoneNumber}")
    public ResponseEntity<Object> getTransaction(@PathVariable String phoneNumber)
    {
         List<TransactionResponseData> t=transactionService.getData(phoneNumber);
         if(walletService.checkUserExist(phoneNumber))
         {
             logger.debug(phoneNumber);
             return transactionResponse.getResponse(t,"Retrieval of Data is successful");
         }
        return transactionResponse.getResponse(t,"Unsuccessful, User does not exist");
    }
    @GetMapping("/transactionId/{transaction_id}")
    public ResponseEntity<?> getSingleTransaction(@PathVariable long transaction_id)
    {
        if(transactionService.chekTransctionById(transaction_id)){
            TransactionData t=transactionService.getById(transaction_id);
            return transactionResponse.getByIdResponse(t,"Data Fetching successful",HttpStatus.FOUND);
        }
            return transactionResponse.getByIdResponse(null,"No transaction exist with id :"+transaction_id,HttpStatus.NOT_FOUND);
    }

    @PostMapping("/transaction")
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionData t) {

//     PostResponse postResponse=new PostResponse();
//    Map<Integer, PostResponseInterface>map=new HashMap<Integer,PostResponseInterface>();
//      map.put(1,new User_Not_Active());
//      map.put(2,new Insufficient_Balance());
//      map.put(3,new Transaction_Succesfull());
//      map.put(4,new User_Not_Exist());

          DateResponse dateResponse=new DateResponse();
        String date=dateResponse.getCurrentDateTime();
        t.setDate(date);
        if(walletService.checkForSame(t))
        {
            return transactionResponse.getPostResponse(t,"Transaction Unsuccessful,Phone Number is Same",HttpStatus.BAD_REQUEST);
        }
        if(walletService.CheckUserExistByTransactionEntity(t))
        {
            if(walletService.checkforcurrentinactive(t.getReceiver(),t.getSender()))
            {
                return transactionResponse.getPostResponse(t,"Transaction Unsuccessful,User is not Active",HttpStatus.NOT_FOUND);
            }
            else if(walletService.CheckSufficientBalance(t.getSender(),t.getAmount()))
            {
                return transactionResponse.getPostResponse(t,"Transaction Unsuccessful,Insufficient Balance",HttpStatus.BAD_REQUEST);
            }
                transactionService.createtransaction(t);
                return transactionResponse.getPostResponse(t,"Transaction Successful",HttpStatus.CREATED);
        }

         return transactionResponse.getPostResponse(t,"Transaction Unsuccessful,User does not exist",HttpStatus.NOT_FOUND);
    }
}
