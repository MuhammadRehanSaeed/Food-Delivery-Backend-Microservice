package com.rehancode.user_service.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rehancode.user_service.Entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
 Optional<UserEntity> findByName(String username);
     Optional<UserEntity> findByEmail(String email);
}
