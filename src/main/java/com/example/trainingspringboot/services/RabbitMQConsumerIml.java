package com.example.trainingspringboot.services;

import com.example.trainingspringboot.model.request.MessageRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumerIml implements RabbitMQConsumer{
    @Override
    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receivedMessage(MessageRequest message) {
        System.out.println("Received Message From RabbitMQ: " + message);
    }
}
