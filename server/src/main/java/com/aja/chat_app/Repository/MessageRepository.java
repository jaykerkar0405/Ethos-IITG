package com.aja.chat_app.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.aja.chat_app.Enity.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message,Long>{
    Optional<Message> findByChatId(Long ChatId);

}
