package com.example.wetrain.repositories;

import com.example.wetrain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.authentication.jaas.JaasAuthenticationCallbackHandler;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value="SELECT * FROM user u WHERE u.username = ?1", nativeQuery = true)
    User findByUsername(String s);
}
