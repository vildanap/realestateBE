package com.example.nekretnine.Controller;

import com.example.nekretnine.Model.Location;
import com.example.nekretnine.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locations")
public class LocationController {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    // -------------------Retrieve All Locations---------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Iterable<Location>> listAllLocations() {
        Iterable<Location> locations = locationRepository.findAll();
        if (locations.spliterator().getExactSizeIfKnown() < 1) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Iterable<Location>>(locations, HttpStatus.OK);
    }
}
