package com.example.wetrain.repositories;

import com.example.wetrain.models.Echipa;
import com.example.wetrain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.authentication.jaas.JaasAuthenticationCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;
//data access layer for the User model
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value="SELECT * FROM user u WHERE u.username = ?1", nativeQuery = true)
    User findByUsername(String s);
    List<User> findUtilizatoriByEchipeId(Long echipaId);

}
