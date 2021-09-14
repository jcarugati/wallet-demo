package com.wallet.lemon.movements;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovementRepository extends JpaRepository<Movement, Integer> {

    @Query(value = "SELECT * FROM movements m WHERE type IN :types AND currency IN :currencies AND user_id = :user_id ORDER BY created DESC", nativeQuery = true)
    List<Movement> findByMovementTypesAndUserId(@Param("types") List<String> types, @Param("user_id") Integer userId, @Param("currencies") List<String> currencies);

    @Query(value = "SELECT * FROM movements m WHERE type IN :types AND user_id = :user_id", nativeQuery = true)
    List<Movement> findByMovementTypesAndUserId(@Param("types") List<String> types, @Param("user_id") Integer userId, Pageable pageable);
}
