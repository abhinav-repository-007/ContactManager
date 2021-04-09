package com.smart.smartcontactmanager.com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.com.smart.smartcontactmanager.entities.Contact;
import com.smart.smartcontactmanager.com.smart.smartcontactmanager.entities.User;
import com.smart.smartcontactmanager.com.smart.smartcontactmanager.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

    //open setting handler
    @GetMapping("/settings")
    public String openSetting(){

        return "settings";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword")String newPassword,
                                 Principal principal,HttpSession session){
        System.out.println("OLD password "+oldPassword);
        System.out.println("new password "+newPassword);
        String name =principal.getName();

        User user=   this.userRepository.getUserByUserName(name);
        System.out.println(user.getPassword());
       if(this.passwordEncoder.matches(oldPassword,user.getPassword())){
           user.setPassword(this.passwordEncoder.encode(newPassword));

           this.userRepository.save(user);
           session.setAttribute("message",new Message("Password succefully chnaged!!","alert-success"));
       }else {
           session.setAttribute("message",new Message("Please enter correct old password!","danger"));
       }

        return "redirect:/user/index";
    }


}
