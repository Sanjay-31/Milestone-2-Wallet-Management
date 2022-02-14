package com.walletdemo.walletdemoproject.Strategy;

import com.walletdemo.walletdemoproject.Model.TransactionData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class User_Not_Active implements PostResponseInterface{
    @Override
    public ResponseEntity<?> getPostResponse(TransactionData t) {
        Map<String,Object> map=new HashMap<>();
        map.put("Transaction",t);
        map.put("Status","Transaction Unsuccessful,User is not Active");
        return new ResponseEntity<Object>(map,HttpStatus.BAD_GATEWAY);
    }
}
