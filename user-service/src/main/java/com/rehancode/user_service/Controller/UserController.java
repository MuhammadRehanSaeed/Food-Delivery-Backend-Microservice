package com.rehancode.user_service.Controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import com.rehancode.user_service.DTO.LoginRequest;
import com.rehancode.user_service.DTO.LoginResponse;
import com.rehancode.user_service.DTO.RegisterRequest;
import com.rehancode.user_service.DTO.RegisterResponse;
import com.rehancode.user_service.Exceptions.ApiResponse;
import com.rehancode.user_service.Service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
       private static final Logger logger =
            LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> registerUser(@Valid @RequestBody RegisterRequest req) {
          logger.info("Register Request Received | Username: {} | Email: {}",
                req.getUsername(), req.getEmail());

        RegisterResponse response = userService.registerUser(req);
         logger.info("User Registered Successfully | Username: {}",
                req.getUsername());
        ApiResponse<RegisterResponse> apiResponse = ApiResponse.<RegisterResponse>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .success(true)
                .message("User registered successfully")
                .data(response)
                .build();

     return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@RequestBody LoginRequest req) {
        
        logger.info("Login Attempt | Username: {}",
                req.getUsername());
        LoginResponse response = userService.loginUser(req);
          logger.info("Login Successful | Username: {}",
                req.getUsername());

        ApiResponse<LoginResponse> apiResponse = ApiResponse.<LoginResponse>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .success(true)
                .message("Login successful")
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}