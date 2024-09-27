package com.aja.chat_app;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aja.chat_app.Enity.AppUser;
import com.aja.chat_app.Enity.Status;
import com.aja.chat_app.Repository.AppUserRepository;


@SpringBootApplication
public class ChatAppApplication implements CommandLineRunner {

	 @Autowired
	 AppUserRepository appUserRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChatAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		AppUser afnan = new AppUser(null, "afnan",null, null,Status.ONLINE);
		AppUser jay = new AppUser(null, "jay", null, null,Status.ONLINE);
		AppUser yash = new AppUser(null, "yash", null, null,Status.ONLINE);
		AppUser anish = new AppUser(null, "anish", null, null,Status.ONLINE);
		appUserRepository.save(afnan);
		appUserRepository.save(jay);
		appUserRepository.save(yash);
		appUserRepository.save(anish);
		
		
		
		
	}

}
