package com.smart.smartcontactmanager.com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.com.smart.smartcontactmanager.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

   // @Autowired
  /* private UserRepository userRepository;

    @GetMapping("/test")
    @ResponseBody
   public String test(){
       User user = new User();
       user.setName("Abhi");
       user.setEmail("abhi@gmail.com");
       userRepository.save(user);

       return "working";
   }*/
  @RequestMapping("/home")
  public String home(Model model){
      model.addAttribute("title","Home - Smart Contact Manager");
      return "home";
  }

    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("title","About - Smart Contact Manager");
        return "about";
    }

}
