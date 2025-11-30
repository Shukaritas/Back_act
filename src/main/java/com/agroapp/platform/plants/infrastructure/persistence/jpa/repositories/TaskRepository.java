package com.agroapp.platform.plants.infrastructure.persistence.jpa.repositories;

import com.agroapp.platform.plants.domain.model.aggregates.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByFieldId(Long fieldId);
}

