package com.rehancode.order_service.DTO;
import java.util.List;

import lombok.Data;

@Data
public class OrderDto {
    private Long userId;
    private Long restaurantId;
    private List<OrderItemDto> items;


}
