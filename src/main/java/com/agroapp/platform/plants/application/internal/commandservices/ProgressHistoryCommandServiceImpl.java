package com.agroapp.platform.plants.application.internal.commandservices;

import com.agroapp.platform.plants.domain.model.commands.CreateProgressHistoryCommand;
import com.agroapp.platform.plants.domain.model.commands.UpdateProgressHistoryCommand;
import com.agroapp.platform.plants.domain.model.entities.ProgressHistory;
import com.agroapp.platform.plants.domain.services.ProgressHistoryCommandService;
import com.agroapp.platform.plants.infrastructure.persistence.jpa.repositories.ProgressHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProgressHistoryCommandServiceImpl implements ProgressHistoryCommandService {

    private final ProgressHistoryRepository progressHistoryRepository;

    public ProgressHistoryCommandServiceImpl(ProgressHistoryRepository progressHistoryRepository) {
        this.progressHistoryRepository = progressHistoryRepository;
    }

    @Override
    public Optional<ProgressHistory> handle(CreateProgressHistoryCommand command) {
        ProgressHistory progressHistory = new ProgressHistory(
                command.fieldId(),
                command.watered(),
                command.fertilized(),
                command.pests()
        );

        ProgressHistory savedProgressHistory = progressHistoryRepository.save(progressHistory);
        return Optional.of(savedProgressHistory);
    }

    @Override
    public Optional<ProgressHistory> handle(UpdateProgressHistoryCommand command) {
        Optional<ProgressHistory> progressHistoryOptional = progressHistoryRepository.findById(command.progressHistoryId());

        if (progressHistoryOptional.isEmpty()) {
            throw new RuntimeException("ProgressHistory not found");
        }

        ProgressHistory progressHistory = progressHistoryOptional.get();
        progressHistory.update(command.watered(), command.fertilized(), command.pests());
        ProgressHistory updatedProgressHistory = progressHistoryRepository.save(progressHistory);
        return Optional.of(updatedProgressHistory);
    }
}

