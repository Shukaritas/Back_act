package com.agroapp.platform.plants.domain.services;

import com.agroapp.platform.plants.domain.model.aggregates.Field;
import com.agroapp.platform.plants.domain.model.queries.*;
import com.agroapp.platform.plants.domain.model.queries.GetAllFieldsQuery;
import com.agroapp.platform.plants.domain.model.queries.GetFieldByIdQuery;
import com.agroapp.platform.plants.domain.model.queries.GetFieldsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface FieldQueryService {
    List<Field> handle(GetAllFieldsQuery query);
    Optional<Field> handle(GetFieldByIdQuery query);
    List<Field> handle(GetFieldsByUserIdQuery query);
}

