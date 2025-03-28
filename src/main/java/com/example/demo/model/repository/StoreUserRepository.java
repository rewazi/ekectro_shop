package com.example.demo.model.repository;

import com.example.demo.model.entity.StoreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StoreUserRepository extends JpaRepository<StoreUser, Long> {
    Optional<StoreUser> findByUsername(String username);
}
