package com.rehancode.user_service.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String token;
    private String username;
    private String email;
    private String phone;
}