package com.rehancode.restaurant_service.DTO;

import lombok.Data;

@Data
public class RestaurantRequest {
    private String name;
    private String location;
    private Boolean open;

}
