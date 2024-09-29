package com.aja.chat_app.Repository;


import java.io.ObjectInputFilter.Status;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aja.chat_app.Enity.AppUser;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser,String>{
     List<AppUser> findAllByStatus(com.aja.chat_app.Enity.Status online);

}
