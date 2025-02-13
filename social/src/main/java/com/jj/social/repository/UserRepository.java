package com.jj.social.repository;

import com.jj.social.dto.user.UserProfileDto;
import com.jj.social.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);

    public Optional<User> findByEmail(String email);

    @Query(value = "SELECT i.id AS userId, i.bio, i.nickname, i.profileImageUri, i.username, i.website, " +
            "COUNT(s1.toUserId) AS subscribeCount, " +
            "CASE WHEN COUNT(DISTINCT s2.fromUserId) > 0 THEN 1 ELSE 0 END AS subscribeState " +
            "FROM User i " +
            "LEFT JOIN Subscribe s1 ON s1.fromUserId = i.id " +
            "LEFT JOIN Subscribe s2 ON s2.fromUserId = :requestUserId AND s2.toUserId = :pageUserId " +
            "WHERE i.id = :pageUserId " +
            "GROUP BY i.id",
    nativeQuery = true)
    public Optional<UserProfileDto> findUserProfile(@Param("pageUserId") Long pageUserId,
                                                    @Param("requestUserId") Long requestUserId);

}
