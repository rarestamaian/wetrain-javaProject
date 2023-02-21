package com.example.wetrain.controllers;

import com.example.wetrain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepo;
//    @RequestParam means it is a parameter from the GET or POST request - noi nu am folosit deocamdata
    @GetMapping({"/", "/index"})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        System.out.println("home coontroller triggered");
        return modelAndView;
    }
}
