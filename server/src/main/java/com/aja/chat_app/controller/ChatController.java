package com.aja.chat_app.controller;

import org.springframework.web.bind.annotation.RestController;

import com.aja.chat_app.Enity.Chat;
import com.aja.chat_app.Enity.Message;
import com.aja.chat_app.Service.ChatServiceImplementation;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@AllArgsConstructor
@RequestMapping("/api/Chat")
public class ChatController {
    ChatServiceImplementation chatServiceImplementation;

    @PostMapping("/")
    public ResponseEntity<Chat> CreateChat(@Valid @RequestBody Chat c) {
       return new ResponseEntity<>( chatServiceImplementation.createChat(c), HttpStatus.CREATED);
    }

    @GetMapping("/getMessages/{chat_id}/user/{user_id}")
    public ResponseEntity<List<Message>> getChats(@PathVariable Long chat_id ,@PathVariable String user_id) {
        return new ResponseEntity<>(chatServiceImplementation.getMessages(chat_id,user_id),HttpStatus.OK); 
    }
    

    @GetMapping("/Friends/user/{user_id}")
    public ResponseEntity<List<Chat>> getMethodName(@PathVariable String user_id) {
        return new ResponseEntity<>(chatServiceImplementation.getallchatsOfUser(user_id),HttpStatus.OK);
    }
    
    



}
