package com.aja.chat_app.exception;

public class UsernameAlreadyExist extends RuntimeException {
    
    public  UsernameAlreadyExist(String username){
            super( username + " already exists ");
    }
}
