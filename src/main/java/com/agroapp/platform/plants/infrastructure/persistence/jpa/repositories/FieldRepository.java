package com.agroapp.platform.plants.infrastructure.persistence.jpa.repositories;

import com.agroapp.platform.plants.domain.model.aggregates.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByUserId(Long userId);
}

