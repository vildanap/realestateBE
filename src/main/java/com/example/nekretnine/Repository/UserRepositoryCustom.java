package com.example.nekretnine.Repository;

import com.example.nekretnine.Model.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> login(String username, String password);
    List<User> findByUsername(String username);
    List<User> findUserById(Long id);
}
