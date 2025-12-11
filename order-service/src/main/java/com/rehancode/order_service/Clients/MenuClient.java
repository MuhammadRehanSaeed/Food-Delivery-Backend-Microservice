package com.rehancode.order_service.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.rehancode.order_service.DTO.MenuDto;
import com.rehancode.order_service.Exceptions.ApiResponse;

@FeignClient(name = "menu-service", configuration = FeignAuthInterceptor.class)
public interface MenuClient {

    @GetMapping("api/menu/{id}")
    ApiResponse<MenuDto> getMenuById(@PathVariable Long id);

}
