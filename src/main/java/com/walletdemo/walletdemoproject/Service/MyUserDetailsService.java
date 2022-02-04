package com.walletdemo.walletdemoproject.Service;

import com.walletdemo.walletdemoproject.Entity.WalletEntity;
import com.walletdemo.walletdemoproject.Repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private WalletRepo walletRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WalletEntity w=walletRepo.getByPhoneNumber(username);
        if(!walletRepo.existsById(username))
        {
            throw new UsernameNotFoundException("User Not found");
        }
        else
        {
          return new User(w.getPhoneNumber(), w.getPassword(), new ArrayList<>());
        }
    }
}
