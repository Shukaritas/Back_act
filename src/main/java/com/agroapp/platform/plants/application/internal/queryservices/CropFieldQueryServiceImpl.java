package com.agroapp.platform.plants.application.internal.queryservices;

import com.agroapp.platform.plants.domain.model.entities.CropField;
import com.agroapp.platform.plants.domain.model.queries.*;
import com.agroapp.platform.plants.domain.model.queries.GetAllCropFieldsQuery;
import com.agroapp.platform.plants.domain.model.queries.GetCropFieldByFieldIdQuery;
import com.agroapp.platform.plants.domain.model.queries.GetCropFieldByIdQuery;
import com.agroapp.platform.plants.domain.services.CropFieldQueryService;
import com.agroapp.platform.plants.infrastructure.persistence.jpa.repositories.CropFieldRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CropFieldQueryServiceImpl implements CropFieldQueryService {

    private final CropFieldRepository cropFieldRepository;

    public CropFieldQueryServiceImpl(CropFieldRepository cropFieldRepository) {
        this.cropFieldRepository = cropFieldRepository;
    }

    @Override
    public List<CropField> handle(GetAllCropFieldsQuery query) {
        return cropFieldRepository.findAll();
    }

    @Override
    public Optional<CropField> handle(GetCropFieldByIdQuery query) {
        return cropFieldRepository.findById(query.cropFieldId());
    }

    @Override
    public Optional<CropField> handle(GetCropFieldByFieldIdQuery query) {
        return cropFieldRepository.findByFieldId(query.fieldId());
    }
}

