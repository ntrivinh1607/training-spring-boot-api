package com.example.trainingspringboot.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreatingRequest {
    @NotNull(message = "Role's name is required")
    @NotEmpty(message = "Role's name is required")
    private String name;

    private List<Integer> permissions;
}
