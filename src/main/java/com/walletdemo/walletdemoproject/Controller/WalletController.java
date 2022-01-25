package com.walletdemo.walletdemoproject.Controller;

import com.walletdemo.walletdemoproject.Entity.WalletEntity;
import com.walletdemo.walletdemoproject.Repository.WalletRepo;
import com.walletdemo.walletdemoproject.ResponseClass.WalletResponse;
import com.walletdemo.walletdemoproject.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@RestController
public class WalletController {

    @Autowired
    WalletService walletService;
    @Autowired
    WalletRepo walletRepo;


    WalletResponse walletResponse=new WalletResponse();
    @GetMapping("/wallet")
    public List<WalletEntity> getAllData()
    {
    return walletRepo.findAll();
    }

    @PostMapping("/wallet")
    public ResponseEntity<Object> createWallet(@RequestBody WalletEntity ww)
    {
        if(walletService.checkUserExist(ww.getPhoneNumber()))
            return walletResponse.getResponse(ww,"User Already Exist");
        else
        {
            walletService.addWallet(ww);
            return walletResponse.getResponse(ww,"Wallet Created Successfully");
        }
    }
    @DeleteMapping("/wallet/{phoneNumber}")
    public ResponseEntity<Object> delete(@PathVariable String phoneNumber)
    {
        WalletEntity w=walletService.get(phoneNumber);
        walletService.update(w);
//        w.setStatus("Inactive");
//        walletService.addWallet()
        return walletResponse.getResponse(w,"Successfully Logged Out");
    }
}
