package com.example.nekretnine.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Advert.class)
public class UserAdvert {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "useradvert_generator")
    @SequenceGenerator(name="useradvert_generator", sequenceName = "useradvert_seq", allocationSize=1)
    private long id;

    private long advertId;

    private long userId;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getAdvertId() { return advertId; }

    public void setAdvertId(long advertId) { this.advertId = advertId; }

    public long getUserId() { return userId; }

    public void setUserId(long userId) { this.userId = userId; }
}
