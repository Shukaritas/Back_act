package com.agroapp.platform.community.infrastructure.persistence.jpa.repositories;

import com.agroapp.platform.community.domain.model.aggregates.CommunityRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRecommendationRepository extends JpaRepository<CommunityRecommendation, Long> {

    /**
     * Updates the userName for all recommendations created by the specified user.
     * This method is used to synchronize userName when a user profile is updated.
     *
     * @param userId the ID of the user whose name needs to be updated
     * @param userName the new userName to set
     */
    @Modifying
    @Query("UPDATE CommunityRecommendation c SET c.userName = :userName WHERE c.userId = :userId")
    void updateUserNameByUserId(@Param("userId") Long userId, @Param("userName") String userName);
}

