package com.walletdemo.walletdemoproject.Service;

import com.walletdemo.walletdemoproject.Model.WalletData;
import com.walletdemo.walletdemoproject.Repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
        WalletData w=walletRepo.getByPhoneNumber(username);
        if(!walletRepo.existsById(username))
        {
            throw new UsernameNotFoundException("User Not found");
        }
          return new User(w.getPhoneNumber(), w.getPassword(), new ArrayList<>());
    }
}
