package com.example.nekretnine.Controller;

import com.example.nekretnine.Messages.CustomErrorType;
import com.example.nekretnine.Model.City;
import com.example.nekretnine.Repository.CityRepository;
import com.example.nekretnine.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {
    private final CityRepository cityRepository;

    @Autowired
    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    // -------------------Retrieve All Cities---------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Iterable<City>> listAllCities() {
        Iterable<City> cities = cityRepository.findAll();
        if (cities.spliterator().getExactSizeIfKnown() < 1) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Iterable<City>>(cities, HttpStatus.OK);
    }


    // -------------------Retrieve One City---------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/{cityId}")
    ResponseEntity<?> getCity (@PathVariable Long cityId) {

        Optional<City> city = this.cityRepository.findById(cityId);
        if (!city.isPresent()) {
            return new ResponseEntity(new CustomErrorType("City with id " + cityId
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<City>>(city, HttpStatus.OK);
    }

}
