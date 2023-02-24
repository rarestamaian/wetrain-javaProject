package com.example.wetrain.controllers;

import com.example.wetrain.models.User;
import com.example.wetrain.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/profile/{id}")
    public String get_user_profile(@PathVariable("id") long id,  Model model) {// findById returns an Optional de care tre sa scapi sa ramana foar obiectul User asa ca mai jos sau poti folosi Optional.get()
        userRepository.findById(id).ifPresent(u -> model.addAttribute("user", u));
        System.out.println("user get + id  controller called");
        return "profile";
    }
    @PostMapping(value = "/update_profile/{id}", params = "update_profile")
    public String update_profile(@PathVariable("id") long id, @Valid User user, // user asta e cel din form iar th:field sunt atributele sale
                                 BindingResult result, Model model) {// findById returns an Optional de care tre sa scapi sa ramana foar obiectul User asa ca mai jos sau poti folosi Optional.get()
        if (result.hasErrors()) {
            user.setId(id);
            return "profile";
        }
        userRepository.save(user);
//        System.out.println(user.toString());
        return "profile";
    }
    @PostMapping(value = "/update_profile/{id}", params = "cancel")
    public String update_profile_cancel(@PathVariable("id") long id, @Valid User user,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "profile";
        }
        userRepository.findById(id).ifPresent(u -> model.addAttribute("user", u));
//        System.out.println(user.toString());
        return "profile";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')") // it s a get request so even though the link is absent for other roles, they could still delete users by writing the url. this prevents it
    @GetMapping(value = "/sterge_user/{id}")
    public String sterge_user(@PathVariable("id") long id, Model model) {// findById returns an Optional de care tre sa scapi sa ramana foar obiectul User asa ca mai jos sau poti folosi Optional.get()
        userRepository.deleteById(id);
        return "redirect:/";
    }
}
