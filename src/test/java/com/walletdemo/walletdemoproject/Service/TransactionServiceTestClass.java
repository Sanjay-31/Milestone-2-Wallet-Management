package com.walletdemo.walletdemoproject.Service;

import com.walletdemo.walletdemoproject.Model.TransactionResponseData;
import com.walletdemo.walletdemoproject.Model.TransactionData;
import com.walletdemo.walletdemoproject.Model.WalletData;
import com.walletdemo.walletdemoproject.Repository.TransactionRepo;
import com.walletdemo.walletdemoproject.Repository.WalletRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionServiceTestClass {

    @Mock
    private TransactionRepo transactionRepo;

    @Mock
    private WalletRepo walletRepo;

    @InjectMocks
    private TransactionService transactionService;


    private TransactionData t1,t2;
    private List<TransactionData>listOfTransaction;
    private WalletData w1,w2;



    @BeforeEach
    void setUp()
    {
        t1=new TransactionData(1,"777","888",100.0);
        t2=new TransactionData(2,"888","777",600.0);
        w1=new WalletData("777",500D,"Active","password","7-2-2022");
        w2=new WalletData("888",200D,"Active","password","8-2-2022");
        listOfTransaction=new ArrayList<TransactionData>();
        listOfTransaction.add(t1);
        listOfTransaction.add(t2);
    }
    @AfterEach
    void TearDown()
    {
        t1=null; w1=null;
        t2=null; w2=null;
        listOfTransaction.clear();
    }
    @Test
    void getTest()
    {
    when(transactionRepo.findAll()).thenReturn(listOfTransaction);
    assertEquals(2,transactionService.get().size());
    }
    @Test
    void createtransactionTest()
    {
        when(walletRepo.findById("777")).thenReturn(java.util.Optional.ofNullable(w1));
        when(walletRepo.findById("888")).thenReturn(java.util.Optional.ofNullable(w2));
        when(transactionRepo.save(t1)).thenReturn(t1);
        TransactionData t=transactionService.createtransaction(t1);
        assertEquals(w1.getAccountBalance(),400D);
        assertEquals(300D,w2.getAccountBalance());
    }
    @Test
    void getDataTest()
    {
        when(transactionRepo.findAll()).thenReturn(listOfTransaction);
        List<TransactionResponseData> transactionData=transactionService.getData("777");
        assertEquals(2,transactionData.size());
    }

}
