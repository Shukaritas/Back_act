package com.agroapp.platform.plants.infrastructure.persistence.jpa.repositories;

import com.agroapp.platform.plants.domain.model.entities.ProgressHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgressHistoryRepository extends JpaRepository<ProgressHistory, Long> {
    Optional<ProgressHistory> findByFieldId(Long fieldId);
}

