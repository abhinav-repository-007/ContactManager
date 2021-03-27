package com.smart.smartcontactmanager.com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.com.smart.smartcontactmanager.entities.Contact;
import com.smart.smartcontactmanager.com.smart.smartcontactmanager.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void addCommanData(Model model,Principal principal){
        String userName =   principal.getName();
        System.out.println("useName "+ userName);
        User userByUserNameName = this.userRepository.getUserByUserName(userName);
        System.out.println("Dao User "+ userByUserNameName);
        model.addAttribute("user",userByUserNameName);


    }

    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal){
        model.addAttribute("title","User Dashboard");
        return "user_dashboard";
    }


    //add form handler
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model){
        model.addAttribute("title","Add-Contact");
        model.addAttribute("contact", new Contact());

        return "add_contact_form";
    }

    //add contact form
    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact, Principal principal){
       String name =principal.getName();

       User user=   this.userRepository.getUserByUserName(name);
       //bidirectional mapping
      contact.setUser(user);
       user.getContacts().add(contact);
       this.userRepository.save(user);

        System.out.println("DATA_ "+contact);

        return "add_contact_form";
    }
}
