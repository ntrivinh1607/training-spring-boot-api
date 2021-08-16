package com.example.trainingspringboot.services;

import com.example.trainingspringboot.model.request.MessageRequest;
import org.springframework.stereotype.Service;

@Service
public interface RabbitMQSender {
    void send(MessageRequest messageRequest);
}
