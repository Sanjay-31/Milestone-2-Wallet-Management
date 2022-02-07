package com.walletdemo.walletdemoproject.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletdemo.walletdemoproject.Entity.TransactionData;
import com.walletdemo.walletdemoproject.Entity.TransactionEntity;
import com.walletdemo.walletdemoproject.Repository.TransactionRepo;
import com.walletdemo.walletdemoproject.ResponseClass.TransactionResponse;
import com.walletdemo.walletdemoproject.Service.TransactionService;
import com.walletdemo.walletdemoproject.Service.WalletService;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    private TransactionEntity t1,t2;
   private List<TransactionEntity>list;
   @Mock
   private TransactionService transactionService;
   @Mock
   private WalletService walletService;
   @InjectMocks
   private TransactionController transactionController;
   @Autowired
   private TransactionResponse transactionResponse;
   @Autowired
    private MockMvc mockMvc;
   @BeforeEach
    void SetUp()
   {
       mockMvc= MockMvcBuilders.standaloneSetup(transactionController).build();
       t1=new TransactionEntity(1,"7354","9755",500D);
       t2=new TransactionEntity(2, "8770", "7354", 300D);
       list=new ArrayList<>();
       list.add(t1);
   }
   @Test
    void getAllTransactionTest() throws Exception
   {
       list.add(t2);
       Mockito.when(transactionService.get()).thenReturn(list);
       mockMvc.perform(
               MockMvcRequestBuilders.get("/transaction")
       ).andExpect(status().isOk());
   }

   @Test
    void getTransaction() throws Exception {
       TransactionData t1=new TransactionData("Received from 9755",500D);
       TransactionData t2=new TransactionData("Sent to 9755",300D);
       List<TransactionData>t=new ArrayList<>();
       t.add(t1);
       t.add(t2);
       Mockito.when(transactionService.getData("7354")).thenReturn(t);
       Mockito.when(walletService.checkUserExist("7354")).thenReturn(true);
       MvcResult mvcResult=mockMvc.perform(
               MockMvcRequestBuilders.get("/transaction/7354")
       ).andExpect(status().isOk()).andReturn();
       System.out.println(mvcResult.getResponse().getContentAsString());
   }

   @Test
    void createTransaction() throws Exception {

       Mockito.when(walletService.checkUserExist(any())).thenReturn(true);
       Mockito.when(walletService.checkforcurrentinactive(any(),any())).thenReturn(false);
       Mockito.when(walletService.sufficientbalance(any(),any())).thenReturn(false);
       mockMvc.perform(
               MockMvcRequestBuilders.post("/transaction")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(asJsonString(t1))
       )
               .andExpect(status().isCreated());
   }
    public static String asJsonString(final Object obj)
    {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
