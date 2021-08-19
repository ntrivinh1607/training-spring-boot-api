package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface RabbitMQSender {
    void sendMessage(String messageContent, User userAddOrUpdateFromRequest, String userMakeRequest);
}
