package com.example.wetrain.services;

import com.example.wetrain.models.Antrernament;
import com.example.wetrain.models.Echipa;
import com.example.wetrain.repositories.AntrenamentRepository;
import com.example.wetrain.repositories.EchipaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    EchipaRepository echipaRepository;
    @Autowired
    AntrenamentRepository antrenamentRepository;
    public Echipa assignAntreToEchipa(Long teamId, Long antreId) {// functie care adauga un antrenament un antrenament deja existent la o echipa deja existenta
        List<Antrernament> antrenamente = null;
        Echipa echipa = echipaRepository.findById(teamId).get();
        Antrernament antrenament = antrenamentRepository.findById(antreId).get();
        antrenamente =  echipa.getAntrenamente();
        antrenamente.add(antrenament);
        echipa.setAntrenamente(antrenamente);
        return echipaRepository.save(echipa);
    }
}
