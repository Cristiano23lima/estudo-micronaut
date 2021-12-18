package com.cristiano.controller;

import com.cristiano.models.Message;
import com.cristiano.services.EmailService;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import lombok.RequiredArgsConstructor;

@KafkaListener(groupId = "disparadorEmail")
@RequiredArgsConstructor
public class EmailListener { 

    private final static Logger LOG = LoggerFactory.getLogger(EmailListener.class);
    
    private final EmailService emailService;

    @Topic("disparar-email")
    public void receive(ConsumerRecord<String, Message> message){
        LOG.debug("Consuming message kafka topic {}", "disparar-email");
        this.emailService.send(message.value());
    }
}
