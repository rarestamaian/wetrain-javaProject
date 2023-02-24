package com.example.wetrain.controllers;

import com.example.wetrain.models.Antrernament;
import com.example.wetrain.models.Echipa;
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
public class TeamController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EchipaRepository echipaRepository;
    @Autowired
    private AntrenamentRepository antrenamentRepository;
    @Autowired
    private ExercitiuRepository exercitiuRepository;
    @GetMapping("/team/{id}")
    public String get_team_details(@PathVariable("id") long id, Model model) {// findById returns an Optional de care tre sa scapi sa ramana foar obiectul User asa ca mai jos sau poti folosi Optional.get()
        Optional<Echipa> echipa_optional = echipaRepository.findById(id);
        if (echipa_optional.isPresent())
        {
            Echipa echipa1 = echipa_optional.get();
            model.addAttribute("team", echipa1);
            List<Antrernament> antrenamente = antrenamentRepository.findAntrenamenteByEchipeId(id);
            model.addAttribute("antrenamente", antrenamente);
//            System.out.println(antrenamente.get(0).toString());
        }
        System.out.println("team get + id  controller called");
        return "team";
    }
    @PostMapping(value = "/update_team/{id}", params = "update_team")
    public String update_team(@PathVariable("id") long id, @Valid Echipa team, // team asta e cel din form iar th:field sunt atributele sale
                              BindingResult result, Model model) {// findById returns an Optional de care tre sa scapi sa ramana foar obiectul User asa ca mai jos sau poti folosi Optional.get()
        if (result.hasErrors()) {
            team.setId(id);
            return "team";
        }
        echipaRepository.save(team);
        model.addAttribute("team", team);
        List<Antrernament> antrenamente = antrenamentRepository.findAntrenamenteByEchipeId(id);
        model.addAttribute("antrenamente", antrenamente);
//        System.out.println(team.toString());
        return "team";
    }
    @PostMapping(value = "/update_team/{id}", params = "cancel")
    public String update_team_cancel(@PathVariable("id") long id, @Valid Echipa team,
                                        BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "team";
        }
        echipaRepository.findById(id).ifPresent(
                u -> model.addAttribute("team", u));
        List<Antrernament> antrenamente = antrenamentRepository.findAntrenamenteByEchipeId(id);
        model.addAttribute("antrenamente", antrenamente);
        return "team";
    }
    @GetMapping("/create_team")
    public String create_team(Model model) {
        Echipa team = new Echipa();
        model.addAttribute("team", team);
        System.out.println(team.toString());
        return "create_team";
    }
    @PostMapping(value = "/create_team", params = "create_team")
    public String create_team_post(@Valid @ModelAttribute Echipa team,
                                     BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "dashboard";
        }
        model.addAttribute("teams", echipaRepository.findAll());
        model.addAttribute("antrenamente", antrenamentRepository.findAll());
        model.addAttribute("exercitii", exercitiuRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        echipaRepository.save(team);
        return "dashboard";
    }
}
