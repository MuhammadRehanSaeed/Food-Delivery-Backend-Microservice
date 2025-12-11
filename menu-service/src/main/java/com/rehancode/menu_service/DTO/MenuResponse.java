package com.rehancode.menu_service.DTO;

import lombok.Data;

@Data
public class MenuResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean available;
    private Long restaurantId;

}
