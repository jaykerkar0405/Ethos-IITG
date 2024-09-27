package com.aja.chat_app.Service;

import java.util.List;
import com.aja.chat_app.Enity.AppUser;
import com.aja.chat_app.Enity.Chat;
import com.aja.chat_app.Enity.Message;
import com.aja.chat_app.Enity.Status;

public interface ChatService {

    public Chat createChat(Chat c);
    public List<Message> getMessages(Long chat_id, Long user_is);
    public List<Chat> getallchatsOfUser(Long user_id);
  
}
