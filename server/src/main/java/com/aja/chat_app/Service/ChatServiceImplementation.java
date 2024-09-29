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
import com.aja.chat_app.exception.UsernameNotFoundEception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ChatServiceImplementation implements ChatService {

    ChatRepository chatRepository;
    AppUserRepository appUserRepository;

    @Override
    public Chat createChat(Chat c) {
      Optional<Chat> isc =   chatRepository.findByUsername1AndUsername2(c.getUsername1(),c.getUsername2());
      if(isc.isPresent()){
         throw new ChatExist(isc.get().getId(), "alredy");
      }
      AppUser u =  AppUserServiceImplementaion.unwrapAppUser(appUserRepository.findById(c.getUsername1()),c.getUsername1());
      AppUserServiceImplementaion.unwrapAppUser(appUserRepository.findById(c.getUsername2()),c.getUsername2());
      c.setAppUser(u);

        return(chatRepository.save(c));
    }

    @Override
    public List<Message> getMessages(Long chat_id, String username) {
        Optional<Chat> c = chatRepository.findById(chat_id);
        AppUser a = AppUserServiceImplementaion.unwrapAppUser(appUserRepository.findById(username), username);
       if(c.isPresent()){
            Chat uc = c.get();
            if(!uc.getUsername1().equals(username) && !uc.getUsername2().equals(username)){
                throw new ChatExist(chat_id,"not present");
            }
           return uc.getMessages();
        }else{
            throw new EntityNotFoundException(chat_id, Chat.class);
        }
    }

    @Override
    public List<Chat> getallchatsOfUser(String user_id) {
        Optional<List<Chat>> chats = chatRepository.findAllByAppUserId(user_id);
        if(chats.isPresent()){
            List<Chat> uchats = chats.get();
            return uchats;
        }
       throw new UsernameNotFoundEception(user_id,AppUser.class);
    }

   

    

}
