package com.walletdemo.walletdemoproject.DTO;

public class PostUserData {
    private String phoneNumber;
    private String password;

   public PostUserData()
    {

    }

    public PostUserData(String phoneNumber, String password) {
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
