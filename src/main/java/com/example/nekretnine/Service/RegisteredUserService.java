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

    public String login(LoginUserForm loginUser) throws ServletException {
        if(loginUser.getUsername() == null || loginUser.getUsername().isEmpty())
            throw new ServletException("Username field cannot be empty");
        if(loginUser.getPassword() == null || loginUser.getUsername().isEmpty())
            throw new ServletException("Password field cannot be empty");

        try {
            List<User> user = userRepository.login(loginUser.getUsername(), loginUser.getPassword());
            if(user!=null) return user.get(1).getUsername();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServletException("RegiseredUserService Login exception");
        }
        return null;
    }
}
