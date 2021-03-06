package com.example.excelProj.Controller;

import com.example.excelProj.Dto.ChatDTO;
import com.example.excelProj.Dto.ChatroomDTO;
import com.example.excelProj.Dto.SearchUserDTO;
import com.example.excelProj.Model.Chat;
import com.example.excelProj.Model.Chatroom;
import com.example.excelProj.Repository.ChatRepository;
import com.example.excelProj.Repository.ChatroomRepository;
import com.example.excelProj.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    ChatService chatService;

    @Autowired
    ChatroomRepository chatroomRepository;

    @Autowired
    ChatRepository chatRepository;

    @MessageMapping("/chat/{to}/{chatroomId}")
    public void sendMessage(@DestinationVariable("to") String to, @DestinationVariable("chatroomId") String chatroomId, @Payload ChatDTO chatDTO) {
        Chat chat = new Chat(chatDTO.getUserId(), chatDTO.getMessage(), chatroomId, new Date(),false);

        simpMessagingTemplate.convertAndSend("/topic/chat/" + to, chat);
        simpMessagingTemplate.convertAndSend("/topic/chatroom/" + chatroomId, chat);

        chatRepository.save(chat);

    }

    @GetMapping("/initiate-chat")
    public ResponseEntity<String> initiateChat(@RequestParam("user1") Long user1, @RequestParam("user2") Long user2) {
        return chatService.initiateChat(user1, user2);
    }

    @GetMapping("/get-all-chats/{chatroomId}/{friendId}")
    public ResponseEntity<List<Chat>> getAllChats(@PathVariable("chatroomId") String chatroomId,@PathVariable("friendId") Long friendId) {
        return chatService.getAllChats(chatroomId,friendId);
    }

    @GetMapping("/seen-all-message")
    public ResponseEntity seenAllUserChatroomMessages(@RequestParam("chatroomId") String chatroomId,@RequestParam("of") Long id)
    {
        return chatService.seenAllUserChatroomMessages(chatroomId,id);
    }

    @GetMapping("/get-all-chatrooms/{id}")
    public ResponseEntity<SearchUserDTO> getAllChatrooms(@PathVariable("id") Long id)
    {
        return chatService.getAllChatrooms(id);
    }

    @GetMapping("/get-chat-count/{id}")
    public ResponseEntity getChatCount(@PathVariable("id") Long id) {
        return chatService.getChatCount(id);
    }



}
