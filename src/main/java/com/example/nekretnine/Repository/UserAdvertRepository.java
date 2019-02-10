package com.example.nekretnine.Repository;

import com.example.nekretnine.Model.UserAdvert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAdvertRepository extends JpaRepository<UserAdvert, Long> {
    List<UserAdvert> findAllByUserId(Long userId);
    Long countUserAdvertByUserId(long userId);
    boolean existsUserAdvertByAdvertIdAndUserId(long advertId, long userId);
    UserAdvert findByAdvertIdAndUserId(long advertId, long userId);
}
