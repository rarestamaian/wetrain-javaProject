package com.example.wetrain.repositories;

import com.example.wetrain.models.Antrernament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AntrenamentRepository extends JpaRepository<Antrernament, Long> {
    List<Antrernament> findAntrenamenteByEchipeId(Long echipaId);
//    List<Antrenament> findAntrenamenteByExercitii(Long antrenamentId);
}
