package com.example.wetrain.controllers;

import com.example.wetrain.models.Antrernament;
import com.example.wetrain.models.Echipa;
import com.example.wetrain.repositories.AntrenamentRepository;
import com.example.wetrain.repositories.EchipaRepository;
import com.example.wetrain.repositories.ExercitiuRepository;
import com.example.wetrain.repositories.UserRepository;
import com.example.wetrain.services.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private TeamService teamService;
    @GetMapping("/team/{id}")
    public String get_team_details(@PathVariable("id") long id, Model model) {// findById returns an Optional de care tre sa scapi sa ramana foar obiectul User asa ca mai jos sau poti folosi Optional.get()
        Optional<Echipa> echipa_optional = echipaRepository.findById(id);
        if (echipa_optional.isPresent())
        {
            Echipa echipa1 = echipa_optional.get();
            model.addAttribute("team", echipa1);
            List<Antrernament> antrenamente = antrenamentRepository.findAntrenamenteByEchipeId(id);
            List<Antrernament> antrenamente_toate = antrenamentRepository.findAll();
            model.addAttribute("antrenamente", antrenamente);
            model.addAttribute("antrenamente_toate", antrenamente_toate);
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
        return "create_team";
    }
    @PostMapping(value = "/create_team", params = "create_team")
    public String create_team_post(@Valid @ModelAttribute Echipa team,
                                     BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "dashboard";
        }
        //aici sa pui daca userul e antrenor atunci cand creeaza echipa,
        // automat el sa fie in echipa si sa poata baga alti sportivi in echipa
        echipaRepository.save(team);
        model.addAttribute("teams", echipaRepository.findAll());
        model.addAttribute("antrenamente", antrenamentRepository.findAll());
        model.addAttribute("exercitii", exercitiuRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "dashboard";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')") // it s a get request so even though the link is absent for other roles, they could still delete users by writing the url. this prevents it
    @GetMapping(value = "/sterge_echipa/{id}")
    public String sterge_echipa(@PathVariable("id") long id, Model model) {// findById returns an Optional de care tre sa scapi sa ramana foar obiectul User asa ca mai jos sau poti folosi Optional.get()
        echipaRepository.deleteById(id);
        return "redirect:/";
    }
    @PostMapping(value = "/adauga_antre_in_echipa/{id}", params = "adauga_antrenament_in_echipa")
    public String adauga_antrenament_in_echipa(@PathVariable("id") long team_id,
                                   @RequestParam(value = "id_antrenament") String antre_id_string,
//                                   BindingResult result,
                                               Model model) {
//        if (result.hasErrors()) {
//            return "dashboard";
//        }
        System.out.println("teamid=" + team_id);
        System.out.println("antre id=" + antre_id_string);
        Long antre_id = Long.parseLong(antre_id_string);
        teamService.assignAntreToEchipa(team_id, antre_id);
//        model.addAttribute("teams", echipaRepository.findAll());
//        model.addAttribute("antrenamente", antrenamentRepository.findAll());
//        model.addAttribute("exercitii", exercitiuRepository.findAll());
//        model.addAttribute("users", userRepository.findAll());
        return "redirect:/dashboard";
    }
}
