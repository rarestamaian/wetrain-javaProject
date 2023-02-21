package com.example.wetrain.repositories;

import com.example.wetrain.models.Exercitiu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExercitiuRepository extends JpaRepository<Exercitiu, Long> {
    List<Exercitiu> findExercitiiByAntrenamenteId(Long antrernamentId);
    // structura e findXByYId(Long yId); unde X si Y sunt List din relatia Many to many
    //de ex y e declarat in modelul Exercitiu.java ca fiind o lista de Antrernamente(am avut un typo in model si l am lasat antrerrnament in loc de antrernament)
    // mai departe face spring select urile
}
