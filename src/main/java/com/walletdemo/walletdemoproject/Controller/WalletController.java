package com.walletdemo.walletdemoproject.Controller;

import com.walletdemo.walletdemoproject.DTO.PostUserData;
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

    DateResponse dateResponse=new DateResponse();
    WalletResponse walletResponse=new WalletResponse();
    @GetMapping("/wallet")
    public ResponseEntity<?> getAllData()
    {
        return walletResponse.getListOfWallet(walletService.getAll(),HttpStatus.FOUND);
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
           return walletResponse.getMessage("Error Message :","Wrong phoneNumber or password",HttpStatus.BAD_REQUEST);
        }
        final UserDetails userDetails=myUserDetailsService.loadUserByUsername(user.getPhoneNumber());
       String token = jwtUtil.generateToken(userDetails);
        logger.info("token generated with phoneNumber "+user.getPhoneNumber()+" token : "+token);
      return walletResponse.getMessage("JWT_Token : ",token,HttpStatus.FOUND);
    }
    @PostMapping("/register")
    public ResponseEntity<Object> createWallet(@RequestBody WalletData ww)
    {
        if(walletService.checkUserByWallet(ww))
        {
            logger.debug("User already exist with phone number : "+ww.getPhoneNumber());
            return walletResponse.getMessage("Error Message :","User with same credentials is already present", HttpStatus.MULTI_STATUS);
        }
        DateResponse dateResponse=new DateResponse();
        String date=dateResponse.getCurrentDateTime();
        ww.setDate(date);
            WalletData newW=walletService.addWallet(ww);
            logger.debug("Wallet created by phone Number : "+ww.getPhoneNumber());
            return walletResponse.getResponse(newW,"Wallet Created Successfully ",HttpStatus.CREATED);
    }
    @DeleteMapping("/wallet/{phoneNumber}")
    public ResponseEntity<Object> delete(@PathVariable String phoneNumber) {
        if(walletService.checkUserExist(phoneNumber))
        {
           WalletData w=walletService.delete(phoneNumber);
            logger.debug("User successfully logged out with phoneNumber : "+phoneNumber);
            return walletResponse.getResponse(w, "Successfully Logged Out", HttpStatus.OK);
        }
        logger.error("User Not exist wth id : "+phoneNumber);
        return walletResponse.getMessage("Error Message : ","Unsuccessful,User Not Found",HttpStatus.NOT_FOUND);
    }
}
