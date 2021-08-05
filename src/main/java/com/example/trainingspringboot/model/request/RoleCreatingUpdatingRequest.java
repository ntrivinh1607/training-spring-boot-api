package com.example.trainingspringboot.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreatingUpdatingRequest {
    private String name;
    private List<Integer> permissions;
}
