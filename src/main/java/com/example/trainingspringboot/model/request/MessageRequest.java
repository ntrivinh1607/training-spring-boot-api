package com.example.trainingspringboot.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
    String content;
    String username;
    Integer userId;
    Date createdDate;
    Date updatedDate;
    Integer createdBy;
    Integer updatedBy;

    @Override
    public String toString() {
        return "Message [content=" + content + "]";
    }
}
