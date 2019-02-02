package com.example.nekretnine.Repository;

import com.example.nekretnine.Model.Advert;
import com.example.nekretnine.Model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
}
