package com.jj.social.service;

import com.jj.social.dto.chat.ChatRoomDto;
import com.jj.social.entity.ChatRoom;
import com.jj.social.entity.ChatRoomUser;
import com.jj.social.repository.ChatRepository;
import com.jj.social.repository.ChatRoomRepository;
import com.jj.social.repository.ChatRoomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;

    private Map<String , ChatRoomDto> chatRoomMap = new HashMap<>();

    // 채팅방 조회
    public List<ChatRoom> findAllRoom(Long userId) {
        List<Long> roomList = chatRoomUserRepository.findChatRoomIdByUserId(userId);
        return chatRoomRepository.findByRoomList(roomList);
    }

}
