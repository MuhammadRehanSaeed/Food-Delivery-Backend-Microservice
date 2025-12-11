package com.rehancode.restaurant_service.DTO;

import lombok.Data;

@Data
public class RestaurantResponse {
    private Long id;
    private String name;
    private String location;
    private Boolean open;
}
