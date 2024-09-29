package com.aja.chat_app.Enity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "AppUser") 
@Table(name = "APP_USERS") 
public class AppUser {

    @Id
    @Column(name = "username",unique = true)
    private String username;

   

    @NotBlank(message="name cannot be empty")
    @Column(name = "name")
    private String name;

   @JsonIgnore
   @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Chat> chats;

    @JsonIgnore
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Message> messages;
    
    @JsonIgnore
    @Column(name = "status ")
    private Status status;
}
