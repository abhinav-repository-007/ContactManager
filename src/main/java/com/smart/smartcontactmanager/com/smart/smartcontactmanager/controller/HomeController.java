package com.smart.smartcontactmanager.com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.com.smart.smartcontactmanager.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @Autowired
   private UserRepository userRepository;

    @GetMapping("/test")
    @ResponseBody
   public String test(){
       User user = new User();
       user.setName("Abhi");
       user.setEmail("abhi@gmail.com");
       userRepository.save(user);

       return "working";
   }
}
