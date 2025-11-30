package com.agroapp.platform.iam.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event triggered when a user profile is updated.
 * Contains the userId and the new userName to allow other bounded contexts to synchronize.
 */
@Getter
public class UserProfileUpdatedEvent extends ApplicationEvent {

    private final Long userId;
    private final String newUserName;

    public UserProfileUpdatedEvent(Object source, Long userId, String newUserName) {
        super(source);
        this.userId = userId;
        this.newUserName = newUserName;
    }
}

