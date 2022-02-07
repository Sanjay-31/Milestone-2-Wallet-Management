package com.walletdemo.walletdemoproject.Service;

import com.walletdemo.walletdemoproject.Entity.WalletEntity;
import com.walletdemo.walletdemoproject.Repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    WalletRepo walletRepo;

    public List<WalletEntity> getAll() {
        return walletRepo.findAll();
    }

    public WalletEntity addWallet(WalletEntity w)
    {
        return walletRepo.save(w);
//        return w;
    }

    public WalletEntity get(String phoneNumber) {
        return walletRepo.findById(phoneNumber).get();
    }

    public void update(WalletEntity w) {
        w.setStatus("Inactive");
        walletRepo.save(w);
    }

    public boolean checkUserExist(String phoneNumber) {
        if(walletRepo.existsById(phoneNumber))
            return true;
        return  false;
    }
    public boolean checkUserByWallet(WalletEntity w) {
        if(walletRepo.existsById(w.getPhoneNumber()))
            return true;
        return  false;
    }

    public boolean checkforcurrentinactive(String toaccount, String fromaccount) {
        if(walletRepo.findById(toaccount).get().getStatus().equals("Inactive")
        ||walletRepo.findById(fromaccount).get().getStatus().equals("Inactive")
        )
            return true;
        return  false;
    }

    public boolean sufficientbalance(String number,Double amount) {
        WalletEntity w=walletRepo.findById(number).get();
    return (w.getAccountBalance()<amount);
    }


}
