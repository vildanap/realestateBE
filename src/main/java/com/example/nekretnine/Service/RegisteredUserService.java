package com.example.nekretnine.Service;

import com.example.nekretnine.Model.LoginUserForm;
import com.example.nekretnine.Model.User;

import com.example.nekretnine.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.List;

@Service
public class RegisteredUserService {
    @Autowired
    private UserRepository userRepository;

    public String login(User loginUser) throws ServletException {
        System.out.println(loginUser.getUsername());
        if(loginUser.getUsername() == null || loginUser.getUsername().isEmpty())
            throw new ServletException("Username field cannot be empty");
        if(loginUser.getPassword() == null || loginUser.getUsername().isEmpty())
            throw new ServletException("Password field cannot be empty");

        try {
            List<User> user = userRepository.login(loginUser.getUsername(), loginUser.getPassword());
            if(!user.isEmpty()) return user.get(0).getUsername();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServletException("RegiseredUserService Login exception");
        }
        return null;
    }

    public User findByUsername(String username) throws ServletException {
        System.out.println(username);

        try {
            List<User> user = userRepository.findByUsername(username);
            if(!user.isEmpty()) {  System.out.println("lista");
                return user.get(0);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServletException("RegiseredUserService Login exception");
        }
        return null;
    }
}
