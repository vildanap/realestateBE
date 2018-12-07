package com.example.nekretnine.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Location.class)
public class Location{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_generator")
    @SequenceGenerator(name="location_generator", sequenceName = "location_seq", allocationSize=1)
    private long id;

    @NotNull(message = "Address cannot be null")
    @Size(min = 4, max = 200, message = "Address must be between 4 and 200 characters")
    private String address;

    @NotNull(message = "Settlement cannot be null")
    @Size(min = 4, max = 200, message = "Settlement must be between 4 and 200 characters")
    private String settlement;

    @JoinColumn(name = "city_id")
    @ManyToOne
    private City city;

    public Location() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
