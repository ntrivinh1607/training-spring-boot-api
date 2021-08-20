package com.example.trainingspringboot.services;

import com.example.trainingspringboot.entities.User;
import com.example.trainingspringboot.model.request.MessageRequest;
import com.example.trainingspringboot.repositories.UserRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;


@Component
public class RabbitMQSenderIml implements RabbitMQSender {
    @Autowired
    private AmqpTemplate myRabbitTemplate;

    @Autowired
    private UserRepository userRepository;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    @Value("${rabbitmq.message.content.update.user}")
    private String contentUpdateUser;

    @Value("${rabbitmq.message.content.signup.user}")
    private String contentSignupUser;

    @Value("${rabbitmq.message.content.create.user}")
    private String contentCreateUser;

    @Value("${rabbitmq.message.content.delete.user}")
    private String contentDeleteUser;

    @Override
    public void sendMessage(String messageContent, User userAddOrUpdateFromRequest, String userMakeRequest) {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setContent(messageContent);
        messageRequest.setUsername(userAddOrUpdateFromRequest.getUsername());
        messageRequest.setUserId(userAddOrUpdateFromRequest.getId());
        messageRequest.setCreatedDate(userAddOrUpdateFromRequest.getCreatedDate());
        messageRequest.setUpdatedDate(userAddOrUpdateFromRequest.getUpdatedDate());

        Optional<User> findUserMakeRequestByName = userRepository.findByUsername(userMakeRequest);
        if (!findUserMakeRequestByName.isPresent()) {
            throw new NoSuchElementException("Not found username");
        }
        Integer userMakeRequestId = findUserMakeRequestByName.get().getId();
        if (messageContent.equals(contentUpdateUser) || messageContent.equals(contentDeleteUser)) {
            messageRequest.setUpdatedBy(userMakeRequestId);
        }
        if (messageContent.equals(contentCreateUser) || messageContent.equals(contentSignupUser)) {
            messageRequest.setCreatedBy(userMakeRequestId);
        }
        myRabbitTemplate.convertAndSend(exchange, routingkey, messageRequest);
    }
}
