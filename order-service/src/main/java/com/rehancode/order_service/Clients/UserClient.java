package com.rehancode.order_service.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rehancode.order_service.DTO.UserDto;
import com.rehancode.order_service.Exceptions.ApiResponse;

@FeignClient(name = "user-service", configuration = FeignAuthInterceptor.class)
public interface UserClient {

    @GetMapping("api/check/{id}")
    ApiResponse<UserDto> getUserById(@PathVariable Long id);

}
