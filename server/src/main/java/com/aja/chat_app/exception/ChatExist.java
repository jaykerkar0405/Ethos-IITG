package com.aja.chat_app.exception;

public class ChatExist extends RuntimeException {

    public ChatExist(Long chat_id,String reason){
        super("You are currently "+ reason +"in the conversation with chat ID: "+chat_id);
    }

}
