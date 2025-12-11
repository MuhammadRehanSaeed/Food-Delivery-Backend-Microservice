package com.rehancode.order_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rehancode.order_service.Entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
