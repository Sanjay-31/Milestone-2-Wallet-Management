package com.walletdemo.walletdemoproject.Strategy;

import com.walletdemo.walletdemoproject.Model.TransactionData;
import com.walletdemo.walletdemoproject.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class Transaction_Succesfull implements PostResponseInterface{
    @Autowired
    private TransactionService transactionService;
    @Override
    public ResponseEntity<?> getPostResponse(TransactionData t) {
        transactionService.createtransaction(t);
        Map<String,Object> map=new HashMap<>();
        map.put("Transaction",t);
        map.put("Status","Transaction Successful");
        return new ResponseEntity<Object>(map,HttpStatus.CREATED);
    }
}
