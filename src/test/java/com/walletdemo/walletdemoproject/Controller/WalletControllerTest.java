package com.walletdemo.walletdemoproject.Controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletdemo.walletdemoproject.Entity.UserRequest;
import com.walletdemo.walletdemoproject.Entity.WalletEntity;
import com.walletdemo.walletdemoproject.ResponseClass.DateResponse;
import com.walletdemo.walletdemoproject.ResponseClass.WalletResponse;
import com.walletdemo.walletdemoproject.Service.WalletService;
import org.apache.catalina.User;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.json.JSONObject;
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
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {
    @Mock
    private WalletService walletService;

    @Mock
    private DateResponse dateResponse;

    @Mock
    private WalletResponse walletResponse;
    @InjectMocks
    private WalletController walletController;

    @Autowired
    private MockMvc mockMvc;

    private List<WalletEntity> listOfWallet;
    private WalletEntity w1,w2;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void SetUp()
    {
        listOfWallet=new ArrayList<WalletEntity>();
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        WalletEntity w1=new WalletEntity("7354",700D,"Active","pass","date");
        WalletEntity w2=new WalletEntity("9755",900D,"Active","pass","date");
        mockMvc= MockMvcBuilders.standaloneSetup(walletController).build();
        listOfWallet.add(w1);
        listOfWallet.add(w2);
    }
     @Test
    void getAllDataTest() throws Exception {
         Mockito.when(walletService.getAll()).thenReturn(listOfWallet);

         ResponseEntity<List<WalletEntity>>res=walletController.getAllData();
         assertEquals(HttpStatus.FOUND,res.getStatusCode());
         assertEquals(2,res.getBody().size());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/wallet")
        )
                .andExpect(status().isFound())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void deleteTest() throws Exception {

        Mockito.when(walletService.get(any())).thenReturn(w1);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/wallet/7354")
        )
                .andExpect(status().isOk());
    }
    @Test
    @Disabled
    void createWalletTest() throws Exception
    {


        Mockito.when(walletService.checkUserByWallet(any())).thenReturn(false);
//        String token=doLogin();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/register")
        ).andExpect(status().isCreated()).andReturn();
//        assertEquals(HttpStatus.CREATED,mvcResult.getResponse().getStatus());
//        assertNotNull(mvcResult);
//        System.out.println("blah "+res.getStatusCode());
//        Mockito.when(walletService.checkUserByWallet(any())).thenReturn(false);
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/register")
//        )
//                .andExpect(status().isCreated());
//        Mockito.when(walletService.checkUserByWallet(any())).thenReturn(false);
//        Mockito.when(dateResponse.getCurrentDateTime()).thenReturn("random_Date");
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/register")
//        )
//                .andExpect(status().isCreated());
//        MvcResult mvcResult=mockMvc.perform(
//                MockMvcRequestBuilders.post("/register")
//        ).andReturn();
//             String content=mvcResult.getResponse().getContentAsString();
//             assertEquals(HttpStatus.CREATED,mvcResult.getResponse().getStatus());
    }
//    public String generateToken() throws Exception
//    {
//        String phoneNumber = "username";
//        UserRequest user = new UserRequest(phoneNumber, "7354");
//        String requestJSON = objectMapper.writeValueAsString(user);
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/token")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(requestJSON))
//                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//        String response = result.getResponse().getContentAsString();
//        assertNotNull(response);
//        JSONObject jObject = new JSONObject(response);
//        return jObject.toString();
//    }
}
