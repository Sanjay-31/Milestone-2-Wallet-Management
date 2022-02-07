package com.walletdemo.walletdemoproject.ResponseClass;


import com.walletdemo.walletdemoproject.Entity.TransactionData;
import com.walletdemo.walletdemoproject.Entity.TransactionEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionResponse {

    public ResponseEntity<Object> getResponse(List<TransactionData> t,String message)
    {
        Map<String,Object> map=new HashMap<>();
        map.put("Transactions",t);
        map.put("Status",message);
        return new ResponseEntity<Object>(map,HttpStatus.OK);
    }
    public ResponseEntity<Object> getPostResponse(TransactionEntity t, String message,HttpStatus httpStatus)
    {
        Map<String,Object> map=new HashMap<>();
        map.put("Transactions",t);
        map.put("Status",message);
        map.put("Status",httpStatus);
        return new ResponseEntity<Object>(map,httpStatus);
    }
}
