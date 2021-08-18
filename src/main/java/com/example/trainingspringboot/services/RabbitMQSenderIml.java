package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.User;
import com.example.trainingspringboot.model.request.MessageRequest;
import com.example.trainingspringboot.model.request.UserCreatingRequest;
import com.example.trainingspringboot.model.request.UserUpdatingRequest;
import com.example.trainingspringboot.repositories.UserRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class RabbitMQSenderIml implements RabbitMQSender{
    @Autowired
    private AmqpTemplate myRabbitTemplate;

    @Autowired
    private UserRepository userRepo;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    @Override
    public void sendMessageForSignup(UserCreatingRequest userCreatingRequest) {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setContent("Sign up User");
        messageRequest.setUsername(userCreatingRequest.getUsername());
        messageRequest.setCreatedDate(new Date());
        myRabbitTemplate.convertAndSend(exchange, routingkey, messageRequest);
    }

    @Override
    public void sendMessageForCreateUser(UserCreatingRequest userCreatingRequest, UserDetails detailUserWhoAction) {
        Optional<User> findUserAction = userRepo.findByUsername(detailUserWhoAction.getUsername());
        if(!findUserAction.isPresent()){
            throw new NoSuchElementException();
        }
        User actionUser = findUserAction.get();

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setContent("Create User");
        messageRequest.setUsername(userCreatingRequest.getUsername());
        messageRequest.setCreatedDate(new Date());
        messageRequest.setCreatedBy(actionUser.getId());
        myRabbitTemplate.convertAndSend(exchange, routingkey, messageRequest);
    }

    @Override
    public void sendMessageForUpdateUser(UserUpdatingRequest userCreatingRequest, UserDetails detailUserWhoAction, Integer userId) {
        Optional<User> user = userRepo.findById(userId);
        if(!user.isPresent()){
            throw new NoSuchElementException();
        }
        Optional<User> findUserAction = userRepo.findByUsername(detailUserWhoAction.getUsername());
        if(!findUserAction.isPresent()){
            throw new NoSuchElementException();
        }
        User affectedUser = user.get();
        User actionUser = findUserAction.get();

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setContent("Update User");
        messageRequest.setUsername(affectedUser.getUsername());
        messageRequest.setUserId(affectedUser.getId());
        messageRequest.setUpdatedBy(actionUser.getId());
        messageRequest.setUpdatedDate(new Date());
        myRabbitTemplate.convertAndSend(exchange, routingkey, messageRequest);
    }

    @Override
    public void sendMessageForDeleteUser(UserDetails detailUserWhoAction, Integer userId) {
        Optional<User> user = userRepo.findById(userId);
        if(!user.isPresent()){
            throw new NoSuchElementException();
        }
        Optional<User> findUserAction = userRepo.findByUsername(detailUserWhoAction.getUsername());
        if(!findUserAction.isPresent()){
            throw new NoSuchElementException();
        }
        User affectedUser = user.get();
        User actionUser = findUserAction.get();

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setContent("Delete User");
        messageRequest.setUsername(affectedUser.getUsername());
        messageRequest.setUserId(affectedUser.getId());
        messageRequest.setUpdatedBy(actionUser.getId());
        messageRequest.setUpdatedDate(new Date());
        myRabbitTemplate.convertAndSend(exchange, routingkey, messageRequest);
    }
}
