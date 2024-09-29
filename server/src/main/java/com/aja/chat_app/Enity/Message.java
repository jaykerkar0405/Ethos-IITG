package com.aja.chat_app.Enity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
@Entity(name = "Message")
@Table(name = "message_table")
public class Message {
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long m_id;

    
    @ManyToOne(optional = false)
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    public Chat chat;

   
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "username")
    public AppUser appUser;


    @NotBlank(message = "message cannot be blank")
    @Column(name = "content")
    public String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    public LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }


}
