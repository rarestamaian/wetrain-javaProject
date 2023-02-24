package com.example.wetrain.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login") // this is for navigating to login page
    public String login( Model model) {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }// this prevents logged in users from accessing the login page
        return "redirect:/";
    }
    @PostMapping("/login") // this is for login page with errors etc
    public String login_post( Model model) {
        System.out.println("login controller called");
            return "login";
    }
}
