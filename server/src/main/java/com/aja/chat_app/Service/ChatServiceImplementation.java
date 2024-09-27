package com.aja.chat_app.Service;

import java.util.*;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.aja.chat_app.Enity.AppUser;
import com.aja.chat_app.Enity.Chat;
import com.aja.chat_app.Enity.Message;
import com.aja.chat_app.Enity.Status;
import com.aja.chat_app.Repository.AppUserRepository;
import com.aja.chat_app.Repository.ChatRepository;
import com.aja.chat_app.exception.ChatExist;
import com.aja.chat_app.exception.EntityNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ChatServiceImplementation implements ChatService {

    ChatRepository chatRepository;
    AppUserRepository appUserRepository;
    private final SimpMessagingTemplate messagingTemplate;
    @Override
    public Chat createChat(Chat c) {
      Optional<Chat> isc =   chatRepository.findByUser1IdAndUser2Id(c.getUser1Id(),c.getUser2Id());
      if(isc.isPresent()){
         throw new ChatExist(isc.get().getId(), "alredy");
      }
      AppUser u =  AppUserServiceImplementaion.unwrapAppUser(appUserRepository.findById(c.getUser1Id()),c.getUser1Id());
      AppUserServiceImplementaion.unwrapAppUser(appUserRepository.findById(c.getUser2Id()),c.getUser2Id());
      c.setAppUser(u);

        return(chatRepository.save(c));
    }

    @Override
    public List<Message> getMessages(Long chat_id, Long user_id) {
        Optional<Chat> c = chatRepository.findById(chat_id);
       if(c.isPresent()){
            Chat uc = c.get();
            if(uc.getUser1Id() != user_id && uc.getUser2Id() != user_id){
                throw new ChatExist(chat_id,"not present");
            }
           return uc.getMessages();
        }else{
            throw new EntityNotFoundException(chat_id, Chat.class);
        }
    }

    @Override
    public List<Chat> getallchatsOfUser(Long user_id) {
        Optional<List<Chat>> chats = chatRepository.findAllByAppUserId(user_id);
        if(chats.isPresent()){
            List<Chat> uchats = chats.get();
            return uchats;
        }
       throw new EntityNotFoundException(user_id,AppUser.class);
    }

   

    

}
