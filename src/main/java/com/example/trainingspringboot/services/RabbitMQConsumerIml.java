package com.example.trainingspringboot.services;

import com.example.trainingspringboot.model.request.MessageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumerIml implements RabbitMQConsumer{
    private static final Logger logger = LogManager.getLogger(RabbitMQConsumerIml.class);
    @Override
    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receivedMessage(MessageRequest message) {
        logger.info(message.toString());
    }
}
