package com.example.wetrain.services;

import com.example.wetrain.models.Antrernament;
import com.example.wetrain.models.Echipa;
import com.example.wetrain.models.User;
import com.example.wetrain.repositories.EchipaRepository;
import com.example.wetrain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EchipaRepository echipaRepository;

    public User assignTeamToUser(Long userId, Long teamId) {// functie care adauga o echipa la un user
        List<Echipa> echipele_userului = null;
        User user = userRepository.findById(userId).get();
        Echipa echipa = echipaRepository.findById(teamId).get();
        echipele_userului =  user.getEchipe();
        echipele_userului.add(echipa);
        user.setEchipe(echipele_userului);
        return userRepository.save(user);
    }
}
