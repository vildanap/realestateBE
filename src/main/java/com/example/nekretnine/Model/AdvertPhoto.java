package com.example.nekretnine.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
public class AdvertPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "advertphoto_generator")
    @SequenceGenerator(name="advertphoto_generator", sequenceName = "advert_seq", allocationSize=1)
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
