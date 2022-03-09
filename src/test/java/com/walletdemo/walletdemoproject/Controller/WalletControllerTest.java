package com.walletdemo.walletdemoproject.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletdemo.walletdemoproject.Model.WalletData;
import com.walletdemo.walletdemoproject.Service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private WalletController walletController;
    @Mock
    private WalletService walletService;
    private List<WalletData>listofwallet;
    private WalletData w1,w2;
    @BeforeEach
    void Setup()
    {
        w1=new WalletData("9090",500D,"Active","pass","date");
        w2=new WalletData("7070",700D,"Active","pass","date");
        listofwallet =new ArrayList<WalletData>();
        listofwallet.add(w1);
        listofwallet.add(w2);
        mockMvc= MockMvcBuilders.standaloneSetup(walletController).build();
    }
    @Test
    void getAllDataTest() throws Exception {

        Mockito.when(walletService.getAll()).thenReturn(listofwallet);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(w1))
        ).andExpect(status().isFound());
    }


    @Test
    void createWalletTest() throws Exception {
        Mockito.when(walletService.checkUserByWallet(any())).thenReturn(false);
        Mockito.when(walletService.addWallet(any())).thenReturn(w1);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/register")
                        .content(asJsonString(w1))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated());
    }

    @Test
    void deleteTest() throws Exception {
        Mockito.when(walletService.checkUserExist(any())).thenReturn(true);
        Mockito.when(walletService.delete(any())).thenReturn(w1);
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/wallet/9090")
                                .content(asJsonString(w1))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
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