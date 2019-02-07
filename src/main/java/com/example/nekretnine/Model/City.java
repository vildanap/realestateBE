package com.example.nekretnine.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_generator")
    @SequenceGenerator(name="city_generator", sequenceName = "city_seq", allocationSize=1)
    private long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 200, message = "Name must be between 1 and 200 characters")
    private String name;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private Set<Location> locations;

    public City(){} // JPA only

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

   /* @Override
    public String toString() {
        String result = String.format(
                "City[id=%d, name='%s']%n",
                id, name);
        if (locations != null) {
            for (Location location : locations) {
                result += String.format(
                        "Location[id=%d, settlement='%s']%n",
                        location.getId(), location.getSettlement());
            }
        }

        return result;
    }*/
}