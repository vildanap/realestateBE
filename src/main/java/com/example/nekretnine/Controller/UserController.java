package com.example.nekretnine.Controller;

import com.example.nekretnine.Messages.CustomErrorType;
import com.example.nekretnine.Model.Location;
import com.example.nekretnine.Model.LoginUserForm;
import com.example.nekretnine.Model.User;
import com.example.nekretnine.Repository.LocationRepository;
import com.example.nekretnine.Repository.UserRepository;

import com.example.nekretnine.Service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    RegisteredUserService registeredUserService;
    private final UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // -------------------Retrieve One User---------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/find/{userId}")
    public ResponseEntity<?> readUser(@PathVariable Long userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if (!user.isPresent()) {
            return new ResponseEntity(new CustomErrorType("User with id " + userId + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
    }
    @RequestMapping(value="/login", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(@RequestBody final LoginUserForm loginUser) throws ServletException {
        String u = registeredUserService.login(loginUser);
        if (u != null) {
            return ResponseEntity.ok(u);
        }
        else
            return ResponseEntity.badRequest().body("Username or password are not correct");
    }
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        userRepository.save(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    // -------------------Update User---------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @Valid @RequestBody User user) {

        Optional<User> currentUser = userRepository.findById(id);

        if (!currentUser.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to update. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentUser.get().setPassword(user.getPassword());
        currentUser.get().setUsername(user.getUsername());
        currentUser.get().setEmail(user.getEmail());
        currentUser.get().setTelephone(user.getTelephone());

        userRepository.save(currentUser.get());
        return new ResponseEntity<Optional<User>>(currentUser, HttpStatus.OK);
    }
}