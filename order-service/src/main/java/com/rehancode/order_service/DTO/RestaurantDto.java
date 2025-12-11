package com.rehancode.order_service.DTO;

import lombok.Data;

@Data
public class RestaurantDto {
    private Long id;
    private String name;
    private String location;
    private Boolean open;


}
