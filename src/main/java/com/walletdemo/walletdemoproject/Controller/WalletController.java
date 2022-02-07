package com.walletdemo.walletdemoproject.Controller;

import com.walletdemo.walletdemoproject.Entity.UserRequest;
import com.walletdemo.walletdemoproject.Entity.WalletEntity;
import com.walletdemo.walletdemoproject.Repository.WalletRepo;
import com.walletdemo.walletdemoproject.ResponseClass.DateResponse;
import com.walletdemo.walletdemoproject.ResponseClass.WalletResponse;
import com.walletdemo.walletdemoproject.Security.JWTUtil;
import com.walletdemo.walletdemoproject.Service.MyUserDetailsService;
import com.walletdemo.walletdemoproject.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//jwt updated

@RestController
public class WalletController {

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
    public ResponseEntity<List<WalletEntity>> getAllData()
    {
     return new ResponseEntity<>(walletService.getAll(),HttpStatus.FOUND);
    }

    @PostMapping("/token")
    public ResponseEntity<?>  getToken(@RequestBody UserRequest user)
    {
        Map<String,String>map=new HashMap<String,String>();
        try {
            authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(user.getPhoneNumber(),user.getPassword()));
        }
        catch (BadCredentialsException e)
        {
//            return walletResponse.getResponse(user,"bad credential buddy");
           map.put("Error Message :","User with this password does not exist");
           return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
        }
        final UserDetails userDetails=myUserDetailsService.loadUserByUsername(user.getPhoneNumber());
       String token = jwtUtil.generateToken(userDetails);
       return new ResponseEntity<>(token,HttpStatus.FOUND);
//       map.put("JWT token : ",token);
//        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<Object> createWallet(@RequestBody WalletEntity ww)
    {
//        System.out.println("Yoo");
        if(walletService.checkUserByWallet(ww))
        {
//            System.out.println("Ye chla");
//            return new ResponseEntity<>(ww,HttpStatus.FOUND);

            return walletResponse.getResponse(ww,"User Already Exist");

        }
        else
        {
//            String date=dateResponse.getCurrentDateTime();
//            ww.setDate(date);
            walletService.addWallet(ww);
             return new ResponseEntity<>(ww,HttpStatus.CREATED);
//            return walletResponse.getResponse(ww,"Wallet Created Successfully ");
        }
    }
    @DeleteMapping("/wallet/{phoneNumber}")
    public ResponseEntity<Object> delete(@PathVariable String phoneNumber) {
        WalletEntity w = walletService.get(phoneNumber);
        walletService.update(w);
//        w.setStatus("Inactive");
//        walletService.addWallet()
        return walletResponse.getResponse(w, "Successfully Logged Out");
    }
}
