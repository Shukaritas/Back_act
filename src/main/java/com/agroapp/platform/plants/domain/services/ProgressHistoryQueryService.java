package com.agroapp.platform.plants.domain.services;

import com.agroapp.platform.plants.domain.model.entities.ProgressHistory;
import com.agroapp.platform.plants.domain.model.queries.*;
import com.agroapp.platform.plants.domain.model.queries.GetAllProgressHistoriesQuery;
import com.agroapp.platform.plants.domain.model.queries.GetProgressHistoryByFieldIdQuery;
import com.agroapp.platform.plants.domain.model.queries.GetProgressHistoryByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProgressHistoryQueryService {
    List<ProgressHistory> handle(GetAllProgressHistoriesQuery query);
    Optional<ProgressHistory> handle(GetProgressHistoryByIdQuery query);
    Optional<ProgressHistory> handle(GetProgressHistoryByFieldIdQuery query);
}

