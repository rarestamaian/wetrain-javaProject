package com.example.wetrain.controllers;

import com.example.wetrain.models.Antrernament;
import com.example.wetrain.models.Echipa;
import com.example.wetrain.models.Exercitiu;
import com.example.wetrain.repositories.AntrenamentRepository;
import com.example.wetrain.repositories.EchipaRepository;
import com.example.wetrain.repositories.ExercitiuRepository;
import com.example.wetrain.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class AntrenamentController {
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private EchipaRepository echipaRepository;
        @Autowired
        private AntrenamentRepository antrenamentRepository;

        @Autowired
        private ExercitiuRepository exercitiuRepository;
        @GetMapping("/antrenament/{id}")
        public String get_antre_details(@PathVariable("id") long id, Model model) {// findById returns an Optional de care tre sa scapi sa ramana doar obiectul User asa ca mai jos sau poti folosi Optional.get()
            Optional<Antrernament> antrenament_optional = antrenamentRepository.findById(id);
            if (antrenament_optional.isPresent())
            {
                Antrernament antrenament1 = antrenament_optional.get();
                model.addAttribute("antre", antrenament1);
                //mai jos faci cu exercitiile din acest antrenament
                List<Exercitiu> exercitii = exercitiuRepository.findExercitiiByAntrenamenteId(id);
                model.addAttribute("exercitii", exercitii);
                System.out.println(exercitii.get(0).toString());
            }
            System.out.println("antre get + id  controller called");
            return "antrenament";
        }
        @PostMapping(value = "/update_antre/{id}", params = "update_antre")
        public String update_antrer(@PathVariable("id") long id, @Valid Antrernament antre, // team asta e cel din form iar th:field sunt atributele sale
                                    BindingResult result, Model model) {// findById returns an Optional de care tre sa scapi sa ramana foar obiectul User asa ca mai jos sau poti folosi Optional.get()
            if (result.hasErrors()) {
                antre.setId(id);
                return "antrernament";
            }
            antrenamentRepository.save(antre);
            model.addAttribute("antre", antre);
//        System.out.println(team.toString());
            return "antrenament";
        }
        @PostMapping(value = "/update_antre/{id}", params = "cancel")
        public String update_antre_cancel(@PathVariable("id") long id, @Valid Antrernament antre,
                                         BindingResult result, Model model) {
            if (result.hasErrors()) {
                return "antrenament";
            }
            antrenamentRepository.findById(id).ifPresent(u -> model.addAttribute("antre", u));
//        System.out.println(user.toString());
            return "antrenament";
        }

    @GetMapping("/create_antrenament")
    public String create_antrenament(Model model) {
        Antrernament antre = new Antrernament();
        model.addAttribute("antre", antre);
        System.out.println(antre);
        return "create_antrenament";
    }
    @PostMapping(value = "/create_antrenament", params = "create_antrenament")
    public String create_antrenament_post(@Valid @ModelAttribute Antrernament antre,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "dashboard";
        }
        antrenamentRepository.save(antre);
        model.addAttribute("teams", echipaRepository.findAll());
        model.addAttribute("antrenamente", antrenamentRepository.findAll());
        model.addAttribute("exercitii", exercitiuRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/dashboard";
    }
    }
