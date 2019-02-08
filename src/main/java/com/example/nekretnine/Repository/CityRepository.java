package com.example.nekretnine.Repository;

import com.example.nekretnine.Model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long>, CityRepositoryCustom {


}
