package com.example.wetrain.repositories;

import com.example.wetrain.models.Antrernament;
import com.example.wetrain.models.Echipa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EchipaRepository extends JpaRepository<Echipa, Long> {
    List<Echipa> findEchipeByAntrenamenteId(Long antrenamentId);
    List<Echipa> findEchipeByUtilizatoriId(Long utilizatorId);
}
