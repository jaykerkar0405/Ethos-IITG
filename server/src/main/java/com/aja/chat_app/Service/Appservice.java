package com.aja.chat_app.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aja.chat_app.Enity.AppUser;
import com.aja.chat_app.Enity.Status;
public interface Appservice {

    public AppUser setAppUser(AppUser user);
    public AppUser getAppUser(String username);
      public AppUser disconnected(AppUser user);
    public List<AppUser> findConnectedUser();

}
