package com.rehancode.user_service.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String username;
    private String email;
    private String phone;
}