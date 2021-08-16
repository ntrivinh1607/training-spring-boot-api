package com.example.trainingspringboot.controllers;

import com.example.trainingspringboot.model.request.MessageRequest;
import com.example.trainingspringboot.services.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {
    @Autowired
    RabbitMQSender rabbitMQSender;

    @GetMapping(value = "/messages")
    public String producer(@RequestParam("title") String title, @RequestParam("content") String content) {

        MessageRequest msg = new MessageRequest();
        msg.setTitle(title);
        msg.setContent(content);
        rabbitMQSender.send(msg);

        return "Message sent to the RabbitMQ JavaInUse Successfully";
    }
}
