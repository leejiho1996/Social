package com.jj.social.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
    private String roomId; // 채팅방 id
    private String roomName; // 채팅방 이름
    private long userCount; // 인원
    private HashMap<String ,String> userList = new HashMap<String, String >();

    public static ChatRoomDto create(String roomName) {
        ChatRoomDto chatroomDto = new ChatRoomDto();
        chatroomDto.setRoomId(UUID.randomUUID().toString());
        chatroomDto.setRoomName(roomName);

        return chatroomDto;
    }
}
