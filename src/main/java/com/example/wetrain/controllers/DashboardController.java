package com.example.wetrain.controllers;

import com.example.wetrain.models.Antrernament;
import com.example.wetrain.models.Echipa;
import com.example.wetrain.models.Exercitiu;
import com.example.wetrain.models.User;
import com.example.wetrain.repositories.AntrenamentRepository;
import com.example.wetrain.repositories.EchipaRepository;
import com.example.wetrain.repositories.ExercitiuRepository;
import com.example.wetrain.repositories.UserRepository;
import com.example.wetrain.securingweb.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

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
    public String register(Model model, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        Long userId = 0L;
        if (principal instanceof CustomUserDetails) {

            userId = ((CustomUserDetails) principal).getUserId();

        }
        Optional<User> user_optional = userRepository.findById(userId);
        if (user_optional.isPresent())
        {
            User user = user_optional.get();
            model.addAttribute("user", user);
            //mai jos faci cu exercitiile din acest antrenament
            List<Echipa> echipe_user = echipaRepository.findEchipeByUtilizatoriId(userId);
            model.addAttribute("echipe_user", echipe_user);
        }
        System.out.println("the user id is below");
        System.out.println(userId);
//        model.addAttribute("user", authentication.getAuthorities());
        model.addAttribute("teams", echipaRepository.findAll());
        model.addAttribute("antrenamente", antrenamentRepository.findAll());
        model.addAttribute("exercitii", exercitiuRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        System.out.println("dashboard get controller called");
        return "dashboard";
    }

}
