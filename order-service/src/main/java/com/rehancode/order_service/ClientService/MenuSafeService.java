package com.rehancode.order_service.ClientService;

import org.springframework.stereotype.Service;

import com.rehancode.order_service.Clients.MenuClient;
import com.rehancode.order_service.DTO.MenuDto;
import com.rehancode.order_service.Exceptions.ApiResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class MenuSafeService {

    private final MenuClient menuClient;

    public MenuSafeService(MenuClient menuClient) {
        this.menuClient = menuClient;
    }

    @CircuitBreaker(name = "menuServiceBreaker", fallbackMethod = "menuFallback")
    @Retry(name = "menuServiceRetry")
    public ApiResponse<MenuDto> getSafeMenu(Long id) {
        return menuClient.getMenuById(id);
    }

    public ApiResponse<MenuDto> menuFallback(Long id, Throwable throwable) {
        log.error("Menu service FAILED for ID {}. Reason: {}", id, throwable.getMessage());

        return ApiResponse.<MenuDto>builder()
                .success(false)
                .status(503)
                .message("Menu service is unavailable. Fallback executed.")
                .data(MenuDto.builder()
                        .menuId(id)
                        .name("Unknown Menu")
                        .price(0.0)
                        .build())
                .build();
    }
}
