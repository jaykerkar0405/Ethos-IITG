package com.aja.chat_app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aja.chat_app.Enity.AppUser;
import com.aja.chat_app.Enity.Status;
import com.aja.chat_app.Service.AppUserServiceImplementaion;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@AllArgsConstructor
@RequestMapping("/api/Appuser")
public class AppUserController {

    AppUserServiceImplementaion appUserServiceImplementaion;

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getAppUser(@PathVariable String id) {
        return new ResponseEntity<>( appUserServiceImplementaion.getAppUser(id),HttpStatus.FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<AppUser> setAppUser(@Valid @RequestBody AppUser user) {
        return new ResponseEntity<>(appUserServiceImplementaion.setAppUser(user),HttpStatus.CREATED);
    }
    


    @GetMapping("/allconnectedusers")
    public ResponseEntity<List<AppUser>> findallconnectedusers(){
        return new ResponseEntity<>(appUserServiceImplementaion.findConnectedUser(),HttpStatus.OK);

    }
    
    

}
