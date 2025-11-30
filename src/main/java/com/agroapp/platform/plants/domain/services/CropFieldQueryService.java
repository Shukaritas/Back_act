package com.agroapp.platform.plants.domain.services;

import com.agroapp.platform.plants.domain.model.entities.CropField;
import com.agroapp.platform.plants.domain.model.queries.*;
import com.agroapp.platform.plants.domain.model.queries.GetAllCropFieldsQuery;
import com.agroapp.platform.plants.domain.model.queries.GetCropFieldByFieldIdQuery;
import com.agroapp.platform.plants.domain.model.queries.GetCropFieldByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CropFieldQueryService {
    List<CropField> handle(GetAllCropFieldsQuery query);
    Optional<CropField> handle(GetCropFieldByIdQuery query);
    Optional<CropField> handle(GetCropFieldByFieldIdQuery query);
}

