package com.example.trainingspringboot.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PermissionCreatingUpdatingRequest {
    @NotNull(message = "Permission's name is required")
    @NotEmpty(message = "Permission's name is required")
    @Size(min = 4, max = 20, message = "Permission's name must between 2 and 20 characters")
    private String name;
}
