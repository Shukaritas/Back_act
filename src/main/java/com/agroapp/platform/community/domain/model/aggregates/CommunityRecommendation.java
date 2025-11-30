package com.agroapp.platform.community.domain.model.aggregates;

import com.agroapp.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * CommunityRecommendation Aggregate Root
 */
@Entity
@Getter
public class CommunityRecommendation extends AuditableAbstractAggregateRoot<CommunityRecommendation> {

    private Long userId;
    private String userName;
    private LocalDateTime commentDate;
    private String comment;

    public CommunityRecommendation() {
        this.commentDate = LocalDateTime.now();
    }

    public CommunityRecommendation(Long userId, String userName, String comment) {
        this.userId = userId;
        this.userName = userName;
        this.commentDate = LocalDateTime.now();
        this.comment = comment;
    }

    public CommunityRecommendation update(String userName, String comment) {
        this.userName = userName;
        this.comment = comment;
        return this;
    }

    public void updateUserName(String userName) {
        this.userName = userName;
    }
}

