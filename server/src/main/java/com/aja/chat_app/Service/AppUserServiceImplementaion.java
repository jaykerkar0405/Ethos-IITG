package com.aja.chat_app.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.aja.chat_app.Enity.AppUser;
import com.aja.chat_app.Enity.Status;
import com.aja.chat_app.Repository.AppUserRepository;
import com.aja.chat_app.exception.EntityNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserServiceImplementaion implements Appservice {

    AppUserRepository appUserRepository;  

    @Override
    public AppUser setAppUser(AppUser user) {
        user.setStatus(Status.ONLINE);
        return(appUserRepository.save(user));
    }

    @Override
    public AppUser getAppUser(Long id) {
         return unwrapAppUser(appUserRepository.findById(id),id); 
    }
    
    static AppUser unwrapAppUser(Optional<AppUser> entity, Long user_id) {
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException(user_id,AppUser.class);
        }
    }
    @Override
    public AppUser disconnected(AppUser user) {
        Optional<AppUser> u =  appUserRepository.findById(user.getId());
        if(u.isPresent()){
            AppUser uAppUser = u.get();
            uAppUser.setStatus(Status.OFLINE);
            return uAppUser;
        }
        throw new EntityNotFoundException(user.getId(), AppUser.class);
    }

    @Override
    public List<AppUser> findConnectedUser() {
       return appUserRepository.findAllByStatus(Status.ONLINE);
       
    }

}
