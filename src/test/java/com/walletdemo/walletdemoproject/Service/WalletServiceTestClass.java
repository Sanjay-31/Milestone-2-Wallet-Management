package com.walletdemo.walletdemoproject.Service;

import com.walletdemo.walletdemoproject.Model.WalletData;
import com.walletdemo.walletdemoproject.Repository.WalletRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WalletServiceTestClass {

    @Mock
    private WalletRepo walletRepo;

    @InjectMocks
    private WalletService walletService;

    private WalletData wallet1,wallet2;
    private List<WalletData>listOfWallet;

    @BeforeEach
    void SetUp()
    {
        listOfWallet=new ArrayList<WalletData>();
        wallet1=new WalletData("777",700.0,"Active","password","7-1-2022");
        wallet2=new WalletData("999",900.0,"Active","password","9-1-2022");
        listOfWallet.add(wallet1);
        listOfWallet.add(wallet2);
    }
    @AfterEach
    void TearDown()
    {
        listOfWallet.clear();
        wallet1=null;
        wallet2=null;
    }

    @Test
    void getAllTest()
    {
        WalletData wallet3=new WalletData("888",800.0,"Active","password","8-1-2022");
        listOfWallet.add(wallet3);
        when(walletRepo.findAll()).thenReturn(listOfWallet);
        assertEquals(walletService.getAll().size(),3);
    }
    @Test
    void addWalletTest()
    {
        when(walletRepo.save(wallet1)).thenReturn(wallet1);
        WalletData w=walletService.addWallet(wallet1);
        assertEquals(w,wallet1);
    }
    @Test
    void getTest()
    {
        when(walletRepo.findById("777")).thenReturn(Optional.ofNullable(wallet1));
        WalletData w=walletService.get("777");
        assertEquals(w,wallet1);
    }
    @Test
    void updateTest()
    {
        walletService.update(wallet2);
        assertEquals(wallet2.getStatus(),"Inactive");
    }
    @Test
    void checkUserExistTest()
    {
        when(walletRepo.existsById("999")).thenReturn(true);
         boolean expected=walletService.checkUserExist("999");
        assertTrue(expected);
    }
    @Test
    void checkforcurrentinactiveTest()
    {
//        wallet1.setStatus("Inactive");
        when(walletRepo.findById("777")).thenReturn(Optional.ofNullable(wallet1));
        when(walletRepo.findById("999")).thenReturn(Optional.ofNullable(wallet2));
        boolean expected=walletService.checkforcurrentinactive("777","999");
        assertEquals(expected,false);
    }
    @Test
    void sufficientbalanceTest()
    {
        when(walletRepo.findById("999")).thenReturn(Optional.ofNullable(wallet2));
        boolean expected=walletService.CheckSufficientBalance("999",150.0);
        assertEquals(expected,false);
    }
}
