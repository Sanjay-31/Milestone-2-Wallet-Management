package com.walletdemo.walletdemoproject;

import com.walletdemo.walletdemoproject.Entity.WalletEntity;
import com.walletdemo.walletdemoproject.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WalletDemoProjectApplication {


	@Autowired
	WalletService walletService;
	public static void main(String[] args) {

		SpringApplication.run(WalletDemoProjectApplication.class, args);

	}

}
