package com.rehancode.order_service.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rehancode.order_service.Clients.MenuClient;
import com.rehancode.order_service.Clients.RestaurantClient;
import com.rehancode.order_service.Clients.UserClient;
import com.rehancode.order_service.DTO.OrderDto;
import com.rehancode.order_service.DTO.OrderItemDto;
import com.rehancode.order_service.DTO.UserDto;
import com.rehancode.order_service.DTO.RestaurantDto;
import com.rehancode.order_service.DTO.MenuDto;

import com.rehancode.order_service.Entity.OrderEntity;
import com.rehancode.order_service.Entity.OrderItems;

import com.rehancode.order_service.Exceptions.ApiResponse;
import com.rehancode.order_service.Exceptions.ResourceNotFoundException;

import com.rehancode.order_service.Repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final RestaurantClient restaurantClient;
    private final MenuClient menuClient;

    public OrderService(OrderRepository orderRepository, UserClient userClient,
                        RestaurantClient restaurantClient, MenuClient menuClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
        this.restaurantClient = restaurantClient;
        this.menuClient = menuClient;
    }

@Transactional
public OrderEntity createOrder(OrderDto dto) {

    // ----------------------
    // Validate USER
    // ----------------------
    ApiResponse<UserDto> userResponse;
    try {
        userResponse = userClient.getUserById(dto.getUserId());
    } catch (Exception ex) {
        throw new ResourceNotFoundException("Failed to fetch user with ID: " + dto.getUserId());
    }
    if (!userResponse.isSuccess() || userResponse.getData() == null) {
        throw new ResourceNotFoundException("User not found with ID: " + dto.getUserId());
    }

    // ----------------------
    // Validate RESTAURANT
    // ----------------------
    ApiResponse<RestaurantDto> restaurantResponse;
    try {
        restaurantResponse = restaurantClient.getRestaurantById(dto.getRestaurantId());
    } catch (Exception ex) {
        throw new ResourceNotFoundException("Failed to fetch restaurant with ID: " + dto.getRestaurantId());
    }
    if (!restaurantResponse.isSuccess() || restaurantResponse.getData() == null) {
        throw new ResourceNotFoundException("Restaurant not found with ID: " + dto.getRestaurantId());
    }

    // ----------------------
    // Convert DTO → ENTITY
    // ----------------------
    OrderEntity order = new OrderEntity();
    order.setUserId(dto.getUserId());
    order.setRestaurantId(dto.getRestaurantId());
    order.setStatus("PENDING");

    // ----------------------
    // Convert Order Items
    // ----------------------
    List<OrderItems> items = dto.getItems().stream().map(itemDto -> {

        ApiResponse<MenuDto> menuRes;
        try {
            menuRes = menuClient.getMenuById(itemDto.getMenuId());
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Failed to fetch menu with ID: " + itemDto.getMenuId());
        }

        if (!menuRes.isSuccess() || menuRes.getData() == null) {
            throw new ResourceNotFoundException("Menu not found with ID: " + itemDto.getMenuId());
        }

        MenuDto menu = menuRes.getData();

        OrderItems item = new OrderItems();
        item.setMenuId(itemDto.getMenuId());
        item.setPrice(menu.getPrice());
        item.setQuantity(itemDto.getQuantity());
        item.setOrder(order);

        return item;
    }).collect(Collectors.toList());

    order.setItems(items);

    // Calculate total amount

    double total = items.stream()
            .mapToDouble(i -> i.getPrice() * i.getQuantity())
            .sum();
    order.setTotalAmount(total);


    return orderRepository.save(order);
}



    public OrderDto convertToDto(OrderEntity order) {
        OrderDto dto = new OrderDto();
        dto.setUserId(order.getUserId());
        dto.setRestaurantId(order.getRestaurantId());

        List<OrderItemDto> itemDtos = order.getItems().stream().map(item -> {
            OrderItemDto d = new OrderItemDto();
            d.setMenuId(item.getMenuId());
            d.setPrice(item.getPrice());
            d.setQuantity(item.getQuantity());
            return d;
        }).collect(Collectors.toList());

        dto.setItems(itemDtos);

        return dto;
    }
    public OrderEntity getOrderById(Long id) {
    return orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));
}
public List<OrderEntity> getAllOrders() {
    return orderRepository.findAll();
}
public List<OrderDto> convertToDtoList(List<OrderEntity> orders) {
    return orders.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
}

}
