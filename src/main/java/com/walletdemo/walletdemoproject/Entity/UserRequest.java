package com.walletdemo.walletdemoproject.Entity;

public class UserRequest {
    private String phoneNumber;
    private String password;

   public  UserRequest()
    {

    }

    public UserRequest(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
