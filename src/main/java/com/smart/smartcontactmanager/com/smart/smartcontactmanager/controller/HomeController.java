package com.smart.smartcontactmanager.com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.com.smart.smartcontactmanager.entities.User;
import com.smart.smartcontactmanager.com.smart.smartcontactmanager.helper.Message;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

   @Autowired
  private UserRepository userRepository;

    /*@GetMapping("/test")
    @ResponseBody
   public String test(){
       User user = new User();
       user.setName("Abhi");
       user.setEmail("abhi@gmail.com");
       userRepository.save(user);

       return "working";
   }*/
  @RequestMapping("")
  public String home(Model model){
      model.addAttribute("title","Home - Smart Contact Manager");
      return "home";
  }

    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("title","About - Smart Contact Manager");
        return "about";
    }

    @RequestMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title","Register - Smart Contact Manager");
        model.addAttribute("user",new User());
        return "signup";
    }
    //this handeler for registering user
    @PostMapping("/do_register")
    public String registerUser(@ModelAttribute("user") User user,
                               @RequestParam(value = "agreement",
                                defaultValue = "false")boolean agreement,
                               Model model, HttpSession session){
try {
    if (!agreement) {
        System.out.println("You have not agreed the trems and conditions");
        throw new Exception("you have not agreed the terms and condtions");
    }
    user.setRole("ROLE_USER");
    user.setEnabled(true);
    user.setImage("default.png");
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    System.out.println("agreement " + agreement);
    System.out.println("user " + user);
    User userObj = this.userRepository.save(user);
    model.addAttribute("user", new User());
    session.setAttribute("message",new Message("Successfully Registered!!","alert-success"));
}catch (Exception ex){
    ex.printStackTrace();
    model.addAttribute("user",user);
    session.setAttribute("message",new Message("Something went worng!"+ex.getMessage(),"alert-danger"));
    return "signup";
}
      return "signup";

    }


    @GetMapping("/signin")
    public  String customLogin(Model model){
      model.addAttribute("title","LoginPage");
      return "login";
    }

}
