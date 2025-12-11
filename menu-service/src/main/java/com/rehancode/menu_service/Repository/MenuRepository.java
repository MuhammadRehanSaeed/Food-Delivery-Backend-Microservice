package com.rehancode.menu_service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rehancode.menu_service.Entity.MenuEntity;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
   List<MenuEntity> findByRestaurantId(Long restaurantId);
}
