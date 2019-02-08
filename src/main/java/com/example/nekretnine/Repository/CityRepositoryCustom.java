package com.example.nekretnine.Repository;

import com.example.nekretnine.Model.City;
import com.example.nekretnine.Model.Location;

import java.util.List;

public interface CityRepositoryCustom {

    List<City> findByCityId(Long id);
}
