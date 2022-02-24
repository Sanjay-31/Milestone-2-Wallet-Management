package com.walletdemo.walletdemoproject.Controller;

import com.walletdemo.walletdemoproject.Model.PostUserData;
import com.walletdemo.walletdemoproject.Model.WalletData;
import com.walletdemo.walletdemoproject.Repository.WalletRepo;
import com.walletdemo.walletdemoproject.ResponseClass.DateResponse;
import com.walletdemo.walletdemoproject.ResponseClass.WalletResponse;
import com.walletdemo.walletdemoproject.Security.JWTUtil;
import com.walletdemo.walletdemoproject.Service.MyUserDetailsService;
import com.walletdemo.walletdemoproject.Service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WalletController {

    Logger logger = LogManager.getLogger(WalletController.class);
    @Autowired
    WalletService walletService;
    @Autowired
    WalletRepo walletRepo;

    @Autowired
     private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
     private JWTUtil jwtUtil;
    Map<String,String>map=new HashMap<String,String>();
    Map<String,String>map1=new HashMap<String,String>();

    DateResponse dateResponse=new DateResponse();
    WalletResponse walletResponse=new WalletResponse();
    @GetMapping("/wallet")
    public ResponseEntity<?> getAllData()
    {
        Map<String,Object>map=new HashMap<String,Object>();
        map.put("Status","Retrieval of data is successful");
        map.put("List Of wallet",walletService.getAll());
     return new ResponseEntity<>(map,HttpStatus.FOUND);
    }

    @PostMapping("/token")
    public ResponseEntity<Object> getToken(@RequestBody PostUserData user)
    {
        try {
            authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(user.getPhoneNumber(),user.getPassword()));
        }
        catch (BadCredentialsException e)
        {
            logger.error("wrong phoneNumber or password , phoneNumber : "+user.getPhoneNumber());
           map.put("Error Message :","Wrong phoneNumber or password");
           return new ResponseEntity<>(map,HttpStatus.MULTI_STATUS);
        }
        final UserDetails userDetails=myUserDetailsService.loadUserByUsername(user.getPhoneNumber());
       String token = jwtUtil.generateToken(userDetails);
       map1.put("JWT_token : ",token);
        logger.info("token generated with phoneNumber "+user.getPhoneNumber()+" token : "+token);
        return new ResponseEntity<>(map1, HttpStatus.FOUND);
    }
    @PostMapping("/register")
    public ResponseEntity<Object> createWallet(@RequestBody WalletData ww)
    {

        if(walletService.checkUserByWallet(ww))
        {
            logger.debug("User already exist with phone number : "+ww.getPhoneNumber());
            map.put("Error Message :","User with same credentials is already present");
            return new ResponseEntity<>(map,HttpStatus.MULTI_STATUS);
        }
        else
        {
            String date=dateResponse.getCurrentDateTime();
            ww.setDate(date);
            WalletData newW=walletService.addWallet(ww);
            logger.debug("Wallet created by phone Number : "+ww.getPhoneNumber());
            return walletResponse.getResponse(newW,"Wallet Created Successfully ",HttpStatus.CREATED);
        }
    }
    @DeleteMapping("/wallet/{phoneNumber}")
    public ResponseEntity<Object> delete(@PathVariable String phoneNumber) {
        if(walletService.checkUserExist(phoneNumber))
        {
            WalletData w = walletService.get(phoneNumber);
            WalletData w1=walletService.update(w);
            logger.debug("User successfully logged out with phoneNumber : "+phoneNumber);
            return walletResponse.getResponse(w1, "Successfully Logged Out",HttpStatus.OK);
        }
        logger.error("User Not exist wth id : "+phoneNumber);
         return new ResponseEntity<>("Unsuccessful,User Not Found",HttpStatus.NOT_FOUND);
    }
}
