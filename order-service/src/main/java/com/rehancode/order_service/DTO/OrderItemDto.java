package com.rehancode.order_service.DTO;


import lombok.Data;

@Data
public class OrderItemDto {
    private Long menuId;
    private double price;
    private int quantity;
}
