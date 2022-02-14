package com.walletdemo.walletdemoproject.Service;

import com.walletdemo.walletdemoproject.Model.TransactionData;
import com.walletdemo.walletdemoproject.Model.WalletData;
import com.walletdemo.walletdemoproject.Repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    WalletRepo walletRepo;

    public List<WalletData> getAll() {
        return walletRepo.findAll();
    }

    public WalletData addWallet(WalletData w)
    {
        return walletRepo.save(w);
    }

    public WalletData get(String phoneNumber) {
        return walletRepo.findById(phoneNumber).get();
    }

    public WalletData update(WalletData w) {
        w.setStatus("Inactive");
        return walletRepo.save(w);
    }

    public boolean checkUserExist(String phoneNumber) {
       return walletRepo.existsById(phoneNumber);

    }

    public boolean CheckUserExistByTransactionEntity(TransactionData transactionEntity)
    {
        return checkUserExist(transactionEntity.getSender())&&checkUserExist(transactionEntity.getReceiver());
    }

    public boolean checkUserByWallet(WalletData w) {
        return walletRepo.existsById(w.getPhoneNumber());
    }

    public boolean checkforcurrentinactive(String toaccount, String fromaccount) {
        if(walletRepo.findById(toaccount).get().getStatus().equals("Inactive")
        ||walletRepo.findById(fromaccount).get().getStatus().equals("Inactive")
        )
            return true;
        return  false;
    }

    public boolean CheckSufficientBalance(String number,Double amount) {
        WalletData w=walletRepo.findById(number).get();
       return (w.getAccountBalance()<amount);
    }


    public boolean checkForSame(TransactionData t) {
        return t.getSender().equals(t.getReceiver());
    }
}
