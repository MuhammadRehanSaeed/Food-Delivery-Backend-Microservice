package com.rehancode.menu_service.Entity;


import com.rehancode.menu_service.DTO.RestaurantDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
     private String name;
    @Column(name = "description", nullable = false)
    private String description;
  @Column(name = "price", nullable = false)
private double price; 

  @Column(name = "available", nullable = false)
private boolean available; 

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;


}
