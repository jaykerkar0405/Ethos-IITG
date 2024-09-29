package com.aja.chat_app.Service;

import java.util.List;
import org.springframework.stereotype.Service;
import java.util.*;
import com.aja.chat_app.Enity.AppUser;
import com.aja.chat_app.Enity.Chat;
import com.aja.chat_app.Enity.Message;
import com.aja.chat_app.Repository.AppUserRepository;
import com.aja.chat_app.Repository.ChatRepository;
import com.aja.chat_app.Repository.MessageRepository;
import com.aja.chat_app.exception.ChatExist;
import com.aja.chat_app.exception.EntityNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageServiceImplementation implements MessageService{
    
    MessageRepository messageRepository;
    AppUserRepository appUserRepository;
    ChatRepository chatRepository;

    @Override
    public Message send(Message message,Long chat_id, String user_id) {
        AppUser user   =  AppUserServiceImplementaion.unwrapAppUser(appUserRepository.findById(user_id),user_id);
        Optional<Chat> c = chatRepository.findById(chat_id);
        if(!c.isPresent()){
            throw new EntityNotFoundException(chat_id, Chat.class);
        }
        Chat uc = c.get();
        if(!uc.getUsername1().equals(user_id) && !uc.getUsername2().equals(user_id)){
            throw new ChatExist(chat_id, "not present");
        }
        message.setAppUser(user);
        message.setChat(uc);
        messageRepository.save(message);
        return message;
    }


}
