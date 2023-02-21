package com.example.wetrain.controllers;

import com.example.wetrain.models.User;
import com.example.wetrain.repositories.AntrenamentRepository;
import com.example.wetrain.repositories.EchipaRepository;
import com.example.wetrain.repositories.ExercitiuRepository;
import com.example.wetrain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EchipaRepository echipaRepository;
    @Autowired
    private AntrenamentRepository antrenamentRepository;
    @Autowired
    private ExercitiuRepository exercitiuRepository;
    @GetMapping("/dashboard")
    public String register( Model model) {
        model.addAttribute("teams", echipaRepository.findAll());
        model.addAttribute("antrenamente", antrenamentRepository.findAll());
        model.addAttribute("exercitii", exercitiuRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        System.out.println("dashboard get controller called");
        return "dashboard";
    }
}
