package com.agroapp.platform.plants.application.internal.queryservices;

import com.agroapp.platform.plants.domain.model.aggregates.Field;
import com.agroapp.platform.plants.domain.model.queries.*;
import com.agroapp.platform.plants.domain.model.queries.GetAllFieldsQuery;
import com.agroapp.platform.plants.domain.model.queries.GetFieldByIdQuery;
import com.agroapp.platform.plants.domain.model.queries.GetFieldsByUserIdQuery;
import com.agroapp.platform.plants.domain.services.FieldQueryService;
import com.agroapp.platform.plants.infrastructure.persistence.jpa.repositories.FieldRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FieldQueryServiceImpl implements FieldQueryService {

    private final FieldRepository fieldRepository;

    public FieldQueryServiceImpl(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public List<Field> handle(GetAllFieldsQuery query) {
        return fieldRepository.findAll();
    }

    @Override
    public Optional<Field> handle(GetFieldByIdQuery query) {
        return fieldRepository.findById(query.fieldId());
    }

    @Override
    public List<Field> handle(GetFieldsByUserIdQuery query) {
        return fieldRepository.findByUserId(query.userId());
    }
}

