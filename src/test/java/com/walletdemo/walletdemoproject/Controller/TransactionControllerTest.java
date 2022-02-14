package com.walletdemo.walletdemoproject.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletdemo.walletdemoproject.Model.TransactionResponseData;
import com.walletdemo.walletdemoproject.Model.TransactionData;
import com.walletdemo.walletdemoproject.ResponseClass.TransactionResponse;
import com.walletdemo.walletdemoproject.Service.TransactionService;
import com.walletdemo.walletdemoproject.Service.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    Logger logger= LogManager.getLogger(DemoWalletControllerTest.class);
    private TransactionData t1,t2;
   private List<TransactionData>list;
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
       t1=new TransactionData(1,"7354","9755",500D);
       t2=new TransactionData(2, "8770", "7354", 300D);
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
    void getTransactionByPhoneNumberTest() throws Exception {
       TransactionResponseData t1=new TransactionResponseData("Received from 9755",500D,"date");
       TransactionResponseData t2=new TransactionResponseData("Sent to 9755",300D,"date");
       List<TransactionResponseData>t=new ArrayList<>();
       t.add(t1);
       t.add(t2);
       Mockito.when(transactionService.getData("7354")).thenReturn(t);
       Mockito.when(walletService.checkUserExist("7354")).thenReturn(true);
       MvcResult mvcResult=mockMvc.perform(
               MockMvcRequestBuilders.get("/transaction/7354")
       ).andExpect(status().isOk()).andReturn();
       logger.debug(mvcResult.getResponse().getContentAsString());
   }

   @Test
    void createTransactionTest() throws Exception {

       Mockito.when(walletService.CheckUserExistByTransactionEntity(any())).thenReturn(true);
       Mockito.when(walletService.checkforcurrentinactive(any(),any())).thenReturn(false);
       Mockito.when(walletService.CheckSufficientBalance(any(),any())).thenReturn(false);
       mockMvc.perform(
               MockMvcRequestBuilders.post("/transaction")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(asJsonString(t1))
       )
               .andExpect(status().isCreated());
   }
   @Test
   void getSingleTransactionTest() throws Exception {
       Mockito.when(transactionService.chekTransctionById(2)).thenReturn(true);
       Mockito.when(transactionService.getById(2)).thenReturn(t2);
       mockMvc.perform(
               MockMvcRequestBuilders.get("/transactionId/2")
                       .content(asJsonString(t2))
                       .contentType(MediaType.APPLICATION_JSON)
       )
               .andExpect(status().isFound());
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
