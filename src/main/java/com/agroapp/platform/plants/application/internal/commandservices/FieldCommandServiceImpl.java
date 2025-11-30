package com.agroapp.platform.plants.application.internal.commandservices;

import com.agroapp.platform.plants.domain.model.aggregates.Field;
import com.agroapp.platform.plants.domain.model.commands.CreateFieldCommand;
import com.agroapp.platform.plants.domain.model.entities.ProgressHistory;
import com.agroapp.platform.plants.domain.services.FieldCommandService;
import com.agroapp.platform.plants.infrastructure.persistence.jpa.repositories.FieldRepository;
import com.agroapp.platform.plants.infrastructure.persistence.jpa.repositories.ProgressHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FieldCommandServiceImpl implements FieldCommandService {

    private final FieldRepository fieldRepository;
    private final ProgressHistoryRepository progressHistoryRepository;

    public FieldCommandServiceImpl(FieldRepository fieldRepository, ProgressHistoryRepository progressHistoryRepository) {
        this.fieldRepository = fieldRepository;
        this.progressHistoryRepository = progressHistoryRepository;
    }

    @Override
    @Transactional
    public Optional<Field> handle(CreateFieldCommand command) {
        Field field = new Field(
                command.userId(),
                command.imageUrl(),
                command.name(),
                command.location(),
                command.fieldSize()
        );

        Field savedField = fieldRepository.save(field);

        // Automatically create ProgressHistory for this Field
        ProgressHistory progressHistory = new ProgressHistory(savedField.getId());
        progressHistoryRepository.save(progressHistory);

        return Optional.of(savedField);
    }
}

