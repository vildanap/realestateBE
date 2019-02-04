package com.example.nekretnine.Repository;
import com.example.nekretnine.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>,UserRepositoryCustom {

    Optional<User> findById(@Param("id") Long id);
    boolean existsByUsername(String username);
    boolean existsById(Long id);
    boolean existsByEmail(String username);
   // User findByUsername(String username);

}