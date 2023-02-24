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
public class ExercitiiController {

    @Autowired
    private ExercitiuRepository exercitiuRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AntrenamentRepository antrenamentRepository;
    @Autowired
    EchipaRepository echipaRepository;

    @GetMapping("/exercitiu/{id}")
    public String get_exercitiu_details(@PathVariable("id") long id, Model model) {// findById returns an Optional de care tre sa scapi sa ramana doar obiectul User asa ca mai jos sau poti folosi Optional.get()
        Optional<Exercitiu> exercitiuOptional = exercitiuRepository.findById(id);
        if (exercitiuOptional.isPresent())
        {
            Exercitiu exercitiu1 = exercitiuOptional.get();
            model.addAttribute("exercitiu", exercitiu1);
            // mai jos eventual daca ai seturi si repetari sau cv de genu sau greutate
            System.out.println(exercitiu1.toString());
        }
        System.out.println("exercitiu get + id  controller called");
        return "exercitiu";
    }
    @PostMapping(value = "/update_exercitiu/{id}", params = "update_exercitiu")
    public String update_exercitiu(@PathVariable("id") long id, @Valid Exercitiu exercitiu, // team asta e cel din form iar th:field sunt atributele sale
                              BindingResult result, Model model) {// findById returns an Optional de care tre sa scapi sa ramana foar obiectul User asa ca mai jos sau poti folosi Optional.get()
        if (result.hasErrors()) {
            exercitiu.setId(id);
            return "exercitiu";
        }
        exercitiuRepository.save(exercitiu);
        model.addAttribute("exercitiu", exercitiu);
//        System.out.println(team.toString());
        return "exercitiu";
    }
    @PostMapping(value = "/update_exercitiu/{id}", params = "cancel")
    public String update_exercitiu_cancel(@PathVariable("id") long id, @Valid Exercitiu exercitiu, // team asta e cel din form iar th:field sunt atributele sale
                                   BindingResult result, Model model) {// findById returns an Optional de care tre sa scapi sa ramana foar obiectul User asa ca mai jos sau poti folosi Optional.get()
        if (result.hasErrors()) {
            return "exercitiu";
        }
        exercitiuRepository.findById(id).ifPresent(
                e ->model.addAttribute("exercitiu", e));
        return "exercitiu";
    }
    @GetMapping("/create_exercitiu")
    public String create_exercitiu(Model model) {
        Exercitiu exercitiu = new Exercitiu();
        model.addAttribute("exercitiu", exercitiu);
        return "create_exercitiu";
    }
    @PostMapping(value = "/create_exercitiu", params = "create_exercitiu")
    public String create_exercitiu_post(@Valid @ModelAttribute Exercitiu exercitiu,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "dashboard";
        }
        exercitiuRepository.save(exercitiu);
        //aici sa pui daca userul e antrenor atunci cand creeaza echipa,
        // automat el sa fie in echipa si sa poata baga alti sportivi in echipa
        model.addAttribute("teams", echipaRepository.findAll());
        model.addAttribute("antrenamente", antrenamentRepository.findAll());
        model.addAttribute("exercitii", exercitiuRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/dashboard";
    }
    @GetMapping(value = "/sterge_exercitiu/{id}")
    public String sterge_exercitiu(@PathVariable("id") long id, Model model) {// findById returns an Optional de care tre sa scapi sa ramana foar obiectul User asa ca mai jos sau poti folosi Optional.get()
        exercitiuRepository.deleteById(id);
        return "redirect:/";
    }
}
