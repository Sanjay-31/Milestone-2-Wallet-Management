package com.walletdemo.walletdemoproject.Kafka;

import com.walletdemo.walletdemoproject.Model.WalletData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class Listener {

    Logger logger= LogManager.getLogger(Listener.class);
    @KafkaListener(topics = "Kafka_MileStone_2",groupId = "group_json",containerFactory = "KafkaJsonListenerFactory")
    public void getJsonMessage(WalletData walletData)
    {
        logger.info("Consumed Json Message : "+walletData);
    }
}
