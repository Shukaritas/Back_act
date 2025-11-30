package com.agroapp.platform.iam.application.internal.queryservices;

import com.agroapp.platform.iam.domain.model.aggregates.User;
import com.agroapp.platform.iam.domain.model.queries.*;
import com.agroapp.platform.iam.domain.model.queries.GetUserByEmailQuery;
import com.agroapp.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.agroapp.platform.iam.domain.services.UserQueryService;
import com.agroapp.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public Optional<User> handle(GetUserByEmailQuery query) {
        return userRepository.findByEmail(query.email());
    }
}

