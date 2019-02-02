package com.example.nekretnine.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Advert.class)
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "advert_generator")
    @SequenceGenerator(name="city_generator", sequenceName = "advert_seq", allocationSize=1)
    private long id;

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    private String title;

    @NotNull(message = "Description cannot be null")
    @Size(min = 1, max = 200, message = "Description must be between 1 and 200 characters")
    private String description;

    @NotNull(message = "Advert Type cannot be null")
    private String advertType;

    @NotNull(message = "Property Type cannot be null")
    private String propertyType;

    @NotNull(message = "Price cannot be null")
    private float price;

    @NotNull(message = "Area cannot be null")
    private float area;

    @JoinColumn(name = "location_id")
    @ManyToOne
    private Location location;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @NotNull(message = "Address cannot be null")
    @Size(min = 4, max = 200, message = "Address must be between 4 and 200 characters")
    private String address;

    private int viewsCount;

    @NotNull(message = "Number of rooms cannot be null")
    private int numberOfRooms;

    public String getTitle() { return title; }

    public void seTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getAdvertType() { return advertType; }

    public void setAdvertType(String advertType) { this.advertType = advertType; }

    public String getPropertyType() { return propertyType; }

    public void setPropertyType(String propertyType) { this.propertyType = propertyType; }

    public int getViewsCount() { return viewsCount; }

    public void setViewsCount(int viewsCount) { this.viewsCount = viewsCount; }

    public int getNumberOfRooms() { return numberOfRooms; }

    public void setNumberOfRooms(int numberOfRooms) { this.numberOfRooms = numberOfRooms; }

    public Location getLocation() { return location; }

    public void setLocation(Location city) { this.location = location; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public float getArea() { return area; }

    public void setArea(float area) { this.area = area; }

    public float getPrice() { return price; }

    public void setPrice(float price) { this.price = price; }
}
