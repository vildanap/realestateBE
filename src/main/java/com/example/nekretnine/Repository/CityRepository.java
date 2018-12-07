package com.example.nekretnine.Repository;

import com.example.nekretnine.Model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
