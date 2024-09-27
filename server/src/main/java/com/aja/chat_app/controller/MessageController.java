package com.aja.chat_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aja.chat_app.Enity.Chat;
import com.aja.chat_app.Enity.ChatNotification;
import com.aja.chat_app.Enity.Message;
import com.aja.chat_app.Repository.ChatRepository;
import com.aja.chat_app.Service.ChatServiceImplementation;
import com.aja.chat_app.Service.MessageServiceImplementation;
import com.aja.chat_app.exception.EntityNotFoundException;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@AllArgsConstructor
@RequestMapping("/api/Message")
public class MessageController {
    
   private final SimpMessagingTemplate messagingTemplate;
   MessageServiceImplementation messageServiceImplementation;
   ChatRepository chatRepository;

    @PostMapping("/send/chat/{chat_id}/user/{user_Id}")
    public ResponseEntity<Message> postMethodName(@Valid @RequestBody Message message,@PathVariable Long chat_id , @PathVariable Long user_Id) {
        messagingTemplate.convertAndSend("/topic/chatroom/" + chat_id, message);
        return new ResponseEntity<>(messageServiceImplementation.send(message, chat_id, user_Id),HttpStatus.ACCEPTED);
    }

    @MessageMapping("/chat/{chat_id}/sender/{sender_id}")
    public void processMessage(@Valid @Payload @RequestBody Message message,@PathVariable Long chat_id,@PathVariable Long sender_id){
        messageServiceImplementation.send(message, chat_id, sender_id);
        messagingTemplate.convertAndSendToUser(
            getrecipientid(chat_id, sender_id),"/queue/messages",
            ChatNotification.builder()
            .Chat_id(chat_id.toString())
            .sender_id(sender_id.toString())
            .recipient_id(getrecipientid(chat_id, sender_id))
            .content(message.getContent())
            .build()
        );


    }
    public String getrecipientid(Long chat_id,Long sender_id){
        Optional<Chat> c = chatRepository.findById(chat_id);
        if(c.isPresent()){
            Chat un = c.get();
            Long id = un.getUser1Id();
            if(sender_id==id){
                return un.getUser2Id().toString();
            }else{
                return id.toString();
            }
        }
        throw new EntityNotFoundException(chat_id, Chat.class);
    }
    

}
