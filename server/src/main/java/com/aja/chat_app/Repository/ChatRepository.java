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
        @Query("SELECT c FROM Chat c WHERE c.user1Id = :appUserId OR c.user2Id = :appUserId")
        Optional<List<Chat>> findAllByAppUserId(@Param("appUserId") Long appUserId);
        Optional<Chat> findByUser1IdAndUser2Id(Long userid_1,Long userid_2);
       
        

}
