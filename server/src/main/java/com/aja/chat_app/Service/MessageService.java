package com.aja.chat_app.Service;

import java.util.List;

import com.aja.chat_app.Enity.Message;

public interface MessageService {
     
        public Message send(Message message,Long chat_id, Long user_id);
        

}
