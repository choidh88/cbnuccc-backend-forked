package com.cbnuccc.cbnuccc.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbnuccc.cbnuccc.Model.Login;

public interface LoginJpaRepository extends JpaRepository<Login, Long> {
    // find login data by email and ip.
    Optional<Login> findByEmailAndIp(String email, String ip);
}
