package com.example.wetrain.controllers;

import com.example.wetrain.models.User;
import com.example.wetrain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    UserRepository userRepository;
    @GetMapping("/register")
    public String register( Model model) {
        model.addAttribute("user", new User());
        System.out.println("register get controller called");
        return "register";
    }

    @PostMapping("/register-user")
    public String register_user(User user) {
        System.out.println("register post controller called");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "register-success";
    }
}
