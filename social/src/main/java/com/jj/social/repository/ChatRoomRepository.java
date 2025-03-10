package com.jj.social.repository;

import com.jj.social.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query(value = "select cr from ChatRoom cr where cr.id in :roomList")
    public List<ChatRoom> findByRoomList(@Param("roomList") List<Long> roomList);
}
