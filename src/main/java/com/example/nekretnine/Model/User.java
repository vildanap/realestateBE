package com.example.nekretnine.Model;
import javax.persistence.GeneratedValue;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = User.class)
@Table(name="user_")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "identity_generator")
    @SequenceGenerator(name = "identity_generator", sequenceName = "identity_seq", allocationSize = 1)
    private long id;

    //@NotEmpty(message = "Firstname cannot be empty")
    private String firstName;

    //@NotEmpty(message = "Lastname cannot be empty")
    private String lastName;

    //@NotEmpty(message = "Username cannot be empty")
    //@Size(min = 4, max = 15, message = "Username must be between 4 and 15 characters")
    private String username;

    // @JsonIgnore
    // @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15})", flags = {Pattern.Flag.CASE_INSENSITIVE}, message = "Password must contain: at least one digit(0-9), lowercase character, uppercase character. Length: 6-15 characters!")
    private String password;

    //@Email(message = "Email should be valid")
    private String email;

    // @Pattern(regexp = "(^$|[0-9]{9})")
    //@NotEmpty(message = "Telephone cannot be empty")
    private String telephone;

    protected User() {
    }

    public User(String firstName, String lastName, String username, String password, String email, String telephone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

//TODO: Add part for favourite-> user and advert
}

