package com.example.nekretnine.Repository;

import com.example.nekretnine.Model.Advert;
import com.example.nekretnine.Model.AdvertPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertPhotoRepository extends JpaRepository<AdvertPhoto, Long> {
}
