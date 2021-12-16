package com.cristiano.controller;

import com.cristiano.models.Message;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.http.annotation.Body;
import io.micronaut.messaging.annotation.MessageBody;
import jakarta.inject.Singleton;

@KafkaListener(groupId = "disparadorEmail")
public class EmailListener {
    @Topic("disparar-email-23")
    public void receive(ConsumerRecord<String, Message> message){
        message.value().setFrom("dj7cristiano@gmail.com");
        System.out.println(message.value().getBody());
    }
}
