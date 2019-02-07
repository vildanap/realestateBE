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
    // -------------------Retrieve One User---------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/{username}")
    public ResponseEntity<?> findUser(@PathVariable String username) throws ServletException {
        User user = this.registeredUserService.findByUsername(username);
        if (user==null) {
            return new ResponseEntity(new CustomErrorType("User with username " + username + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody final User loginUser) throws ServletException {
        System.out.println(" Login " );
        User u = registeredUserService.login(loginUser);
        if (u!=null) {
            return new ResponseEntity(u,HttpStatus.OK);
        }
        else
            return new ResponseEntity (HttpStatus.BAD_REQUEST);
    }
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        System.out.println(" Sign up " );
        userRepository.save(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    // -------------------Update User---------------------------------------------
    @RequestMapping(value = "/{username}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("username") String username, @Valid @RequestBody User user) throws ServletException {

        User editedUser = this.registeredUserService.findByUsername(username);

        if (editedUser==null) {
            return new ResponseEntity(new CustomErrorType("Unable to update. User with username " + username + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        editedUser.setFirstName(user.getFirstName());
        editedUser.setLastName(user.getLastName());
        editedUser.setPassword(user.getPassword());
        editedUser.setUsername(user.getUsername());
        editedUser.setEmail(user.getEmail());
        editedUser.setTelephone(user.getTelephone());

        userRepository.save(editedUser);
        return new ResponseEntity<User>(editedUser, HttpStatus.OK);
    }
    // DELETE
    @RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId)  {
        System.out.println("Delete");
        System.out.println(userId);
        Optional<User> user = this.userRepository.findById(userId);
        if (!user.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + userId + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        this.userRepository.deleteById(userId);
        return new ResponseEntity(HttpStatus.OK);
    }
}