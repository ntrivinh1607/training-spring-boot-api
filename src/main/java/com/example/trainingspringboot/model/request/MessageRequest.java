package com.example.trainingspringboot.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
    String title;
    String content;

    @Override
    public String toString() {
        return "Message [title=" + title + ", content=" + content + "]";
    }
}
