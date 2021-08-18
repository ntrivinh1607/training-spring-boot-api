package com.example.trainingspringboot.services;

import com.example.trainingspringboot.model.request.MessageRequest;
import com.example.trainingspringboot.model.request.UserCreatingRequest;
import com.example.trainingspringboot.model.request.UserUpdatingRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface RabbitMQSender {
    void sendMessageForSignup(UserCreatingRequest userCreatingRequest);
    void sendMessageForCreateUser(UserCreatingRequest userCreatingRequest, UserDetails detailUserWhoAction);
    void sendMessageForUpdateUser(UserUpdatingRequest userCreatingRequest, UserDetails detailUserWhoAction, Integer idUser);
    void sendMessageForDeleteUser(UserDetails userDetails, Integer idUser);
}
