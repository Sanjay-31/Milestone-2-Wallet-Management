package com.walletdemo.walletdemoproject.Kafka;


import com.walletdemo.walletdemoproject.Model.WalletData;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class Consumer {

    @Bean
    public ConsumerFactory<String, WalletData>consumerFactory()
    {
        Map<String, Object> config=new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG,"group_json");
        return new DefaultKafkaConsumerFactory<String,WalletData>(config,
                new StringDeserializer(),
                new JsonDeserializer<>(WalletData.class));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,WalletData> KafkaJsonListenerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String,WalletData> factory=new ConcurrentKafkaListenerContainerFactory<String,WalletData>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
