package com.walletdemo.walletdemoproject.ResponseClass;

import com.walletdemo.walletdemoproject.Model.WalletData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletResponse {

    public ResponseEntity<Object> getResponse(WalletData w, String message, HttpStatus httpStatus)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("status",message);
        map.put("WalletData",w);
        return new ResponseEntity<Object>(map,httpStatus);
    }
    public ResponseEntity<Object> getMessage(String errorMessage, String message, HttpStatus httpStatus)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(errorMessage,message);
        return new ResponseEntity<Object>(map,httpStatus);
    }
    public ResponseEntity<Object> getListOfWallet(List<WalletData>list, HttpStatus httpStatus)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("status","Data Fetched Successfully");
        map.put("List Of Wallet : ",list);
        return new ResponseEntity<Object>(map,httpStatus);
    }
}
