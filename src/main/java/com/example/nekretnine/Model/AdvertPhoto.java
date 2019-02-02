package com.example.nekretnine.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Advert.class)
public class AdvertPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "advert_generator")
    @SequenceGenerator(name="city_generator", sequenceName = "advert_seq", allocationSize=1)
    private long id;

    private long advertId;

    private long fileId;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getAdvertId() { return advertId; }

    public void setAdvertId(long advertId) { this.advertId = advertId; }

    public long getFileId() { return fileId; }

    public void setFileId(long fileId) { this.fileId = fileId; }
}
