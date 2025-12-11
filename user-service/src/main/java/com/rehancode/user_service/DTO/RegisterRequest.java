package com.rehancode.user_service.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message="Username is required")
    private String username;
    @NotBlank(message="email is required")
    @Email(message="Invalid email format")
    private String email;
    @NotBlank(message="password is required")
    private String password;
    @NotBlank(message="phone number is required")
    private String phone;
}