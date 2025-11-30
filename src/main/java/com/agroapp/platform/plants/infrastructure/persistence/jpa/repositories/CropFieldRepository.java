package com.agroapp.platform.plants.infrastructure.persistence.jpa.repositories;

import com.agroapp.platform.plants.domain.model.entities.CropField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CropFieldRepository extends JpaRepository<CropField, Long> {
    Optional<CropField> findByFieldId(Long fieldId);
}

