package com.aja.chat_app.Repository;

import java.io.ObjectInputFilter.Status;
import java.util.List;
import java.util.Optional;

import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap.Option;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aja.chat_app.Enity.AppUser;
import com.aja.chat_app.Enity.Chat;

@Repository
public interface ChatRepository extends CrudRepository<Chat,Long> {
        @Query("SELECT c FROM Chat c WHERE c.username1 = :appUserId OR c.username2 = :appUserId")
        Optional<List<Chat>> findAllByAppUserId(@Param("appUserId") String appUserId);
        Optional<Chat> findByUsername1AndUsername2(String userid_1,String username_2 );
       
        

}
