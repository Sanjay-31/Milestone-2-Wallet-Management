package com.walletdemo.walletdemoproject.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletdemo.walletdemoproject.Entity.UserRequest;
import com.walletdemo.walletdemoproject.Entity.WalletEntity;
import com.walletdemo.walletdemoproject.Repository.WalletRepo;
import com.walletdemo.walletdemoproject.Service.WalletService;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemoWalletControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private WalletService walletService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllDataTest() throws Exception {
         String token=generateToken();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/wallet")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        )
                .andExpect(status().isFound());
    }

    @Test

    void deleteTest() throws Exception {

        String token=generateToken();
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/wallet/7354")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        )
                .andExpect(status().isOk());
        WalletEntity w=walletRepo.getByPhoneNumber("7354");
        w.setStatus("Active");
    }


    @Test
    void getTokenTest() throws Exception {

        String phoneNumber = "7354";
        UserRequest user = new UserRequest(phoneNumber, "pass");
        String token=generateToken();
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/token")
                    .content(asJsonString(user))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION,"Bearer "+token)
            ).andExpect(status().isFound());
    }

    @Test
    void createWalletTest() throws Exception
    {
        String token=generateToken();
        WalletEntity w3=new WalletEntity("12345",900D,"Active","pass1234","date");

        //since we permitted the "/register" url , we doesn't need any JWT token.
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(w3))
                ).andExpect(status().isCreated());
        walletRepo.deleteById(w3.getPhoneNumber());
    }
    public String generateToken() throws Exception
    {
        String phoneNumber = "7354";
        UserRequest user = new UserRequest(phoneNumber, "pass");
        objectMapper=new ObjectMapper();
        String requestJSON = objectMapper.writeValueAsString(user);
        MvcResult result = mockMvc.perform(
                post("/token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJSON))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertNotNull(response);

        JSONObject jObject = new JSONObject(response);
        String token= jObject.getString("JWT_token : ");
        System.out.println(token);
        return token;
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
