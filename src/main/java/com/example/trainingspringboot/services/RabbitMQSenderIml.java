package com.example.trainingspringboot.services;

import com.example.trainingspringboot.model.request.MessageRequest;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSenderIml implements RabbitMQSender{
    @Autowired
    private AmqpTemplate myRabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    @Override
    public void send(MessageRequest messageRequest) {
        myRabbitTemplate.convertAndSend(exchange, routingkey, messageRequest);
        System.out.println("Send msg = " + messageRequest);
    }
}
