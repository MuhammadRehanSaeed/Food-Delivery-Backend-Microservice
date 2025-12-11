package com.rehancode.user_service.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressRequest {
    @NotBlank(message="City is required")
    private String city;
     @NotBlank(message="Street is required")
    private String street;
     @NotBlank(message="zipCode is required")
  private String zipCode;  // camelCase
    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be positive")
    private Long userId;

}
