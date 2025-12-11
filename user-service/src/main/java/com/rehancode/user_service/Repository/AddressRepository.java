package com.rehancode.user_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rehancode.user_service.Entity.AddressEntity;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Long>{

}
