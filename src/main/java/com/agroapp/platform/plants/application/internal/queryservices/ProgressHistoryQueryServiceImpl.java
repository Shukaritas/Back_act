package com.agroapp.platform.plants.application.internal.queryservices;

import com.agroapp.platform.plants.domain.model.entities.ProgressHistory;
import com.agroapp.platform.plants.domain.model.queries.*;
import com.agroapp.platform.plants.domain.model.queries.GetAllProgressHistoriesQuery;
import com.agroapp.platform.plants.domain.model.queries.GetProgressHistoryByFieldIdQuery;
import com.agroapp.platform.plants.domain.model.queries.GetProgressHistoryByIdQuery;
import com.agroapp.platform.plants.domain.services.ProgressHistoryQueryService;
import com.agroapp.platform.plants.infrastructure.persistence.jpa.repositories.ProgressHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgressHistoryQueryServiceImpl implements ProgressHistoryQueryService {

    private final ProgressHistoryRepository progressHistoryRepository;

    public ProgressHistoryQueryServiceImpl(ProgressHistoryRepository progressHistoryRepository) {
        this.progressHistoryRepository = progressHistoryRepository;
    }

    @Override
    public List<ProgressHistory> handle(GetAllProgressHistoriesQuery query) {
        return progressHistoryRepository.findAll();
    }

    @Override
    public Optional<ProgressHistory> handle(GetProgressHistoryByIdQuery query) {
        return progressHistoryRepository.findById(query.progressHistoryId());
    }

    @Override
    public Optional<ProgressHistory> handle(GetProgressHistoryByFieldIdQuery query) {
        return progressHistoryRepository.findByFieldId(query.fieldId());
    }
}

