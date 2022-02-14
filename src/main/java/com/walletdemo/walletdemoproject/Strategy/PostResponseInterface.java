package com.walletdemo.walletdemoproject.Strategy;

import com.walletdemo.walletdemoproject.Model.TransactionData;
import org.springframework.http.ResponseEntity;

public interface PostResponseInterface {
    ResponseEntity<?> getPostResponse(TransactionData t);
}
