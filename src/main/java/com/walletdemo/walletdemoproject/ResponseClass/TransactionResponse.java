package com.walletdemo.walletdemoproject.ResponseClass;


import com.walletdemo.walletdemoproject.DTO.TransactionSummaryData;
import com.walletdemo.walletdemoproject.Model.TransactionData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionResponse {

    public ResponseEntity<Object> getResponse(List<TransactionSummaryData> t, String message)
    {
        Map<String,Object> map=new HashMap<>();
        map.put("Transactions",t);
        map.put("Status",message);
        return new ResponseEntity<Object>(map,HttpStatus.OK);
    }
    public ResponseEntity<Object> getPostResponse(TransactionData t, String message, HttpStatus httpStatus)
    {
        Map<String,Object> map=new HashMap<>();
        map.put("Transaction",t);
        map.put("Status",message);
        return new ResponseEntity<Object>(map,httpStatus);
    }

    public ResponseEntity<Object> getByIdResponse(TransactionData t, String message, HttpStatus httpStatus) {
        Map<String,Object> map=new HashMap<>();
        map.put("Transaction",t);
        map.put("Status",message);
        return new ResponseEntity<Object>(map,httpStatus);
    }
    public ResponseEntity<Object> getMessageResponse(String Errormessage, String message, HttpStatus httpStatus) {
        Map<String,Object> map=new HashMap<>();
        map.put(Errormessage,message);
        return new ResponseEntity<Object>(map,httpStatus);
    }
}
