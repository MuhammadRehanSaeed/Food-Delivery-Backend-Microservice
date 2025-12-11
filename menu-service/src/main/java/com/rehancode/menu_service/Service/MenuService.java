package com.rehancode.menu_service.Service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.rehancode.menu_service.Clients.RestaurantClient;
import com.rehancode.menu_service.DTO.MenuRequest;
import com.rehancode.menu_service.DTO.MenuResponse;
import com.rehancode.menu_service.DTO.RestaurantDTO;
import com.rehancode.menu_service.Entity.MenuEntity;
import com.rehancode.menu_service.Repository.MenuRepository;

import feign.FeignException;

import com.rehancode.menu_service.Exceptions.ApiResponse;
import com.rehancode.menu_service.Exceptions.ResourceNotFoundException;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final RestaurantClient restaurantClient;
    public MenuService(MenuRepository menuRepository, RestaurantClient restaurantClient) {
        this.menuRepository = menuRepository;
        this.restaurantClient = restaurantClient;
    }


  public MenuResponse convertToDto(MenuEntity menuEntity) {
    MenuResponse menuResponse = new MenuResponse();

    menuResponse.setId(menuEntity.getId());
    menuResponse.setName(menuEntity.getName());
    menuResponse.setDescription(menuEntity.getDescription());
    menuResponse.setPrice(menuEntity.getPrice());
    menuResponse.setAvailable(menuEntity.isAvailable());
    menuResponse.setRestaurantId(menuEntity.getRestaurantId());

    return menuResponse;
}

 public MenuEntity convertToEntity(MenuRequest menuRequest) {

    // Check restaurant exists (optional)

       try {
        restaurantClient.getRestaurantById(menuRequest.getRestaurantId());
    } catch (FeignException.NotFound e) {
        throw new ResourceNotFoundException("Restaurant not found with ID: " + menuRequest.getRestaurantId());
    } catch (FeignException e) {
        throw new RuntimeException("Failed to communicate with Restaurant Service");
    }

    return MenuEntity.builder()
            .name(menuRequest.getName())
            .description(menuRequest.getDescription())
            .price(menuRequest.getPrice())
            .available(menuRequest.getAvailable())
            .restaurantId(menuRequest.getRestaurantId())
            .build();
}


    public MenuResponse createMenu(MenuRequest menuRequest) {
        MenuEntity menuEntity = convertToEntity(menuRequest);
        MenuEntity savedMenu = menuRepository.save(menuEntity);
        return convertToDto(savedMenu);
    }
    public MenuResponse getMenuById(Long id) {
        MenuEntity menuEntity = menuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        return convertToDto(menuEntity);
    }
public List<MenuResponse> getMenusByRestaurantId(Long restaurantId) {
    // Optional: validate restaurant exists first
    try {
        ApiResponse<RestaurantDTO> restaurantResponse = restaurantClient.getRestaurantById(restaurantId);
        if (restaurantResponse.getData() == null) {
            throw new ResourceNotFoundException("Restaurant not found with ID: " + restaurantId);
        }
    } catch (FeignException.NotFound e) {
        throw new ResourceNotFoundException("Restaurant not found with ID: " + restaurantId);
    }

    // Fetch menus for restaurant
    List<MenuEntity> menus = menuRepository.findByRestaurantId(restaurantId);

    // Convert to DTOs
    return menus.stream()
                .map(this::convertToDto)
                .toList();
}



}
