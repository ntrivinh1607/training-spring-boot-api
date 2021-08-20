package com.example.trainingspringboot.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PermissionCreatingUpdatingRequest {
    @NotBlank(message = "Permission's name is required")
    @Size(min = 4, max = 20, message = "Permission's name must be between 4 and 20 characters")
    private String name;
}
