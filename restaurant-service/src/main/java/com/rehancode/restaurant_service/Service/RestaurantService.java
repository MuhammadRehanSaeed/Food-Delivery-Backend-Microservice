package com.rehancode.restaurant_service.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rehancode.restaurant_service.DTO.RestaurantRequest;
import com.rehancode.restaurant_service.DTO.RestaurantResponse;
import com.rehancode.restaurant_service.Entity.RestaurantEntity;
import com.rehancode.restaurant_service.Exceptions.ResourceNotFoundException;
import com.rehancode.restaurant_service.Repository.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // ===== DTO Conversion =====
    public RestaurantResponse convertToDTO(RestaurantEntity entity) {
        RestaurantResponse dto = new RestaurantResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLocation(entity.getLocation());
        dto.setOpen(entity.getOpen());
        return dto;
    }

    public RestaurantEntity convertToEntity(RestaurantRequest dto) {
        RestaurantEntity entity = new RestaurantEntity();
        entity.setName(dto.getName());
        entity.setLocation(dto.getLocation());
        entity.setOpen(dto.getOpen());
        return entity;
    }

    // ===== CRUD Operations =====
    public RestaurantResponse createRestaurant(RestaurantRequest dto) {
        RestaurantEntity entity = convertToEntity(dto);
        RestaurantEntity saved = restaurantRepository.save(entity);
        return convertToDTO(saved);
    }

    public RestaurantResponse getRestaurantById(Long id) {
        RestaurantEntity entity = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
        return convertToDTO(entity);
    }

    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RestaurantResponse updateRestaurant(Long id, RestaurantRequest dto) {
        RestaurantEntity entity = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));

        entity.setName(dto.getName());
        entity.setLocation(dto.getLocation());
        entity.setOpen(dto.getOpen());

        RestaurantEntity updated = restaurantRepository.save(entity);
        return convertToDTO(updated);
    }

    public void deleteRestaurant(Long id) {
        RestaurantEntity entity = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
        restaurantRepository.delete(entity);
    }
}
