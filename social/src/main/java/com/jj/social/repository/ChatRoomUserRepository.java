package com.jj.social.repository;

import com.jj.social.entity.ChatRoomUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {

    @Query(value = "select roomId from ChatRoomUser where userId = :userId",
    nativeQuery = true)
    List<Long> findChatRoomIdByUserId(@Param("userId") Long userId);


}
