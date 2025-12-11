package com.rehancode.order_service.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.rehancode.order_service.DTO.RestaurantDto;
import com.rehancode.order_service.Exceptions.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-service", configuration = FeignAuthInterceptor.class)
public interface RestaurantClient {

        @GetMapping("/api/restaurants/{id}")
       ApiResponse<RestaurantDto> getRestaurantById(@PathVariable Long id);

}
