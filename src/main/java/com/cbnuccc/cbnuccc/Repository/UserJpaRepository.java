package com.cbnuccc.cbnuccc.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbnuccc.cbnuccc.Model.User;

public interface UserJpaRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
