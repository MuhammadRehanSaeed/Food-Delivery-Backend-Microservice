package com.rehancode.order_service.Fallback;

import com.rehancode.order_service.Clients.MenuClient;
import com.rehancode.order_service.DTO.MenuDto;
import com.rehancode.order_service.Exceptions.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuClientFallback implements MenuClient {

    private final Throwable cause;

    public MenuClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public ApiResponse<MenuDto> getMenuById(Long id) {

        log.error("Menu service FAILED for ID {}. Reason: {}", id, cause.getMessage());

        return ApiResponse.<MenuDto>builder()
                .success(false)
                .status(503)
                .message("Menu service is unavailable. This is fallback data.")
                .data(MenuDto.builder()
                        .menuId(id)
                        .name("Unknown Menu")
                        .price(0.0)
                        .build())
                .build();
    }
}
