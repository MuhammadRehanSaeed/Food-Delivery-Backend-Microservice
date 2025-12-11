package com.rehancode.user_service.Controller;


import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.rehancode.user_service.DTO.AddressRequest;
import com.rehancode.user_service.DTO.AddressResponse;
import com.rehancode.user_service.Exceptions.ApiResponse;
import com.rehancode.user_service.Service.AddressService;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;
    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // Add new address
    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponse>> addAddress(@Valid @RequestBody AddressRequest request) {
        logger.info("Add Address Request | UserId: {}", request.getUserId());
        AddressResponse response = addressService.addAddress(request);

        ApiResponse<AddressResponse> apiResponse = ApiResponse.<AddressResponse>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .success(true)
                .message("Address added successfully")
                .data(response)
                .build();

          return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    // Get address by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> getAddressById(@PathVariable Long id) {
        logger.info("Get Address By ID Request | ID: {}", id);
        AddressResponse response = addressService.getAddressById(id);

        ApiResponse<AddressResponse> apiResponse = ApiResponse.<AddressResponse>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .success(true)
                .message("Address fetched successfully")
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // Get all addresses
    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getAllAddresses() {
        logger.info("Get All Addresses Request");
        List<AddressResponse> responseList = addressService.getAllAddresses();

        ApiResponse<List<AddressResponse>> apiResponse = ApiResponse.<List<AddressResponse>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .success(true)
                .message("All addresses fetched successfully")
                .data(responseList)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}
