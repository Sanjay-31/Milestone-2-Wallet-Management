package com.walletdemo.walletdemoproject.ResponseClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateResponse {

    public String getCurrentDateTime()
    {
        LocalDateTime date =LocalDateTime.now();

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

        return date.format(myFormatObj);
    }
}
