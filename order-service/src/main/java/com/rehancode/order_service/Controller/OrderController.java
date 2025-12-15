package com.rehancode.order_service.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rehancode.order_service.DTO.OrderDto;
import com.rehancode.order_service.Entity.OrderEntity;
import com.rehancode.order_service.Exceptions.ApiResponse;
import com.rehancode.order_service.Service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<OrderDto>> createOrder(@RequestBody OrderDto dto) {

        OrderEntity saved = orderService.createOrder(dto);

        OrderDto responseDto = orderService.convertToDto(saved);

        ApiResponse<OrderDto> res = new ApiResponse<>(
            HttpStatus.CREATED.value(),
            true,
            "Order created successfully",
            responseDto
        );

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
     @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable Long id) {

        OrderEntity found = orderService.getOrderById(id);
        OrderDto dto = orderService.convertToDto(found);

        ApiResponse<OrderDto> response = ApiResponse.<OrderDto>builder()
                .status(200)
                .success(true)
                .message("Order fetched successfully")
                .data(dto)
                .build();

        return ResponseEntity.ok(response);
    }


    // GET ALL ORDERS

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDto>>> getAllOrders() {

        List<OrderEntity> orders = orderService.getAllOrders();
        List<OrderDto> dtos = orderService.convertToDtoList(orders);

        ApiResponse<List<OrderDto>> response = ApiResponse.<List<OrderDto>>builder()
                .status(200)
                .success(true)
                .message("All orders fetched successfully")
                .data(dtos)
                .build();

        return ResponseEntity.ok(response);
    }
}
