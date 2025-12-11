package com.rehancode.user_service.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rehancode.user_service.DTO.UserResponse;
import com.rehancode.user_service.Exceptions.ApiResponse;
import com.rehancode.user_service.Service.UserService;

@RestController
@RequestMapping("api/check")
public class CheckController {
    private final UserService userService;
    public CheckController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String check(){
        return "Hello from spring boot";
    }
    @GetMapping("/{id}")
public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {

    UserResponse response = userService.getUserById(id);

    ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder()
            .status(200)
            .success(true)
            .message("User fetched successfully")
            .data(response)
            .build();

    return ResponseEntity.ok(apiResponse);
}



}