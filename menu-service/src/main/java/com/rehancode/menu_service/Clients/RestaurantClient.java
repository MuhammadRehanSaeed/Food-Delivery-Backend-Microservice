package com.rehancode.menu_service.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rehancode.menu_service.DTO.RestaurantDTO;
import com.rehancode.menu_service.Exceptions.ApiResponse;

@FeignClient(name = "restaurant-service", configuration = FeignAuthInterceptor.class)
public interface RestaurantClient {

    @GetMapping("/api/restaurants/{id}")
    ApiResponse<RestaurantDTO> getRestaurantById(@PathVariable("id") Long id);
}
