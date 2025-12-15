package com.rehancode.order_service.DTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuDto {
    private Long menuId;
    private String name;
    private double price;

}
