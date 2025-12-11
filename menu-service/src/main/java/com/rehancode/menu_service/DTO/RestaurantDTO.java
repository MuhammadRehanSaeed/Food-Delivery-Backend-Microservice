package com.rehancode.menu_service.DTO;

import lombok.Data;

@Data
public class RestaurantDTO {
    private Long id;
    private String name;
    private String location;
    private Boolean open;

}
