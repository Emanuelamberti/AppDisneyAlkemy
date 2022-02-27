package com.appDisney.application.Controller;

import com.appDisney.application.Errors.ErrorServices;
import com.appDisney.application.Services.NotificationServices;
import com.appDisney.application.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PortalController {

    @Autowired
    private UserService userServices;



    //@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
    @GetMapping("/")
    public String indexWithUser(ModelMap model){
        return "indexWithUser";
    }

    //@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
    @GetMapping("/indexWithOutUser")
    public String index(ModelMap model, HttpSession session){
        if (model.getAttribute("title")==null || model.getAttribute("title")=="" || !model.containsAttribute("title")){
            model.addAttribute("title", "You have successfully entered!");
        }
        return "indexWithUser";
    }

    @GetMapping("/login")
    public String login(){

        return "login.html";
    }

    @GetMapping("/logout")
    public String logout(){
        return "logout.html";
    }

    @GetMapping("/register")
    public String register(ModelMap model){

        return "register.html";
    }

    @PostMapping("/register")
    public String register(ModelMap model, @RequestParam String username, @RequestParam String email,
                           @RequestParam String encrypted1,
                           @RequestParam String encrypted2, MultipartFile image){
        try {
            userServices.saveUser(username, email, encrypted1, encrypted2, image);
            model.put("title", username+", \n" + "you have registered successfully!");
            model.put("description", "We have sent an email to "+email+" \n" + "with confirmation of registration.");
            return "redirect:/";
        } catch (ErrorServices ex) {
            model.put("error", ex.getMessage());
            model.put("name",username);
            model.put("email",email);
            model.put("encrypted1",encrypted1);
            model.put("encrypted2", encrypted2);
            model.put("image",image);
            return "redirect:/";
        }
    }


}
