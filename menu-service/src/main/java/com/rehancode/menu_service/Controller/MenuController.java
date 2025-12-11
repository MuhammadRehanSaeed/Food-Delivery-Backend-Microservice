package com.rehancode.menu_service.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rehancode.menu_service.DTO.MenuRequest;
import com.rehancode.menu_service.DTO.MenuResponse;
import com.rehancode.menu_service.Exceptions.ApiResponse;
import com.rehancode.menu_service.Service.MenuService;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // -------------------------
    // Create Menu
    // -------------------------
    @PostMapping
    public ResponseEntity<ApiResponse<MenuResponse>> createMenu(@RequestBody MenuRequest menuRequest) {

        MenuResponse response = menuService.createMenu(menuRequest);

        ApiResponse<MenuResponse> apiResponse = ApiResponse.<MenuResponse>builder()
                .status(201)
                .success(true)
                .message("Menu created successfully")
                .data(response)
                .build();

        return ResponseEntity.status(201).body(apiResponse);
    }

    // -------------------------
    // Get Menu by ID
    // -------------------------
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuResponse>> getMenuById(@PathVariable Long id) {

        MenuResponse response = menuService.getMenuById(id);

        ApiResponse<MenuResponse> apiResponse = ApiResponse.<MenuResponse>builder()
                .status(200)
                .success(true)
                .message("Menu fetched successfully")
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // -------------------------
    // Test: Get Restaurant Info via Feign
    // -------------------------
   @GetMapping("/restaurant/{restaurantId}")
public ResponseEntity<ApiResponse<List<MenuResponse>>> getMenusByRestaurant(@PathVariable Long restaurantId) {

    List<MenuResponse> menus = menuService.getMenusByRestaurantId(restaurantId);

    ApiResponse<List<MenuResponse>> apiResponse = ApiResponse.<List<MenuResponse>>builder()
            .status(200)
            .success(true)
            .message("Menus fetched successfully")
            .data(menus)
            .build();

    return ResponseEntity.ok(apiResponse);
}


}
