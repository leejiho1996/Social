package com.jj.social.controller;

import com.jj.social.auth.PrincipalDetails;
import com.jj.social.dto.chat.ChatRoomDto;
import com.jj.social.entity.ChatRoom;
import com.jj.social.service.ChatService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/chat")
    public String chat(@AuthenticationPrincipal PrincipalDetails principalDetails,
                       Model model) {

        Long userId = principalDetails.getUser().getId();
        List<ChatRoom> allRoom = chatService.findAllRoom(userId);
        model.addAttribute("allRoom", allRoom);
        return "chat/chatroom";
    }


}
