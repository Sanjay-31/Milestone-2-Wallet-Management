package com.walletdemo.walletdemoproject.ResponseClass;

import com.walletdemo.walletdemoproject.Model.WalletData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class WalletResponse {

    public ResponseEntity<Object> getResponse(WalletData w, String message, HttpStatus httpStatus)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("status",message);
        map.put("WalletData",w);
        return new ResponseEntity<Object>(map,httpStatus);
    }
}
