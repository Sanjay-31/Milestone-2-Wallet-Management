package com.walletdemo.walletdemoproject.ResponseClass;

import com.walletdemo.walletdemoproject.Entity.WalletEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class WalletResponse {

    public ResponseEntity<Object> getResponse(WalletEntity w,String message)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("status",message);
        map.put("WalletData",w);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}
