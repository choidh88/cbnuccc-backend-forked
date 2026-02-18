package com.cbnuccc.cbnuccc.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbnuccc.cbnuccc.Model.Prayer;

public interface PrayerJpaRepository extends JpaRepository<Prayer, Integer> {
    // get not anonymous prayers.
    List<Prayer> findAllByAnonymousFalse();

    // get all prayers of specific user.
    List<Prayer> findAllByAuthorUuid(UUID uuid);

    // get a specific prayer but not anonymous.
    Optional<Prayer> findByIdAndAnonymousFalse(Integer id);

    Optional<Prayer> findByIdAndAuthorUuid(Integer id, UUID uuid);
}
