package com.example.nekretnine.Controller;

import com.example.nekretnine.Messages.CustomErrorType;
import com.example.nekretnine.Model.Location;
import com.example.nekretnine.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

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


    // -------------------Retrieve One Location---------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/{locationId}")
    ResponseEntity<?> getLocation (@PathVariable Long locationId) {

        Optional<Location> location = this.locationRepository.findById(locationId);
        if (!location.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Location with id " + locationId
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Location>>(location, HttpStatus.OK);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<?> createLocation(@RequestBody Location location, UriComponentsBuilder ucBuilder) {
        locationRepository.save(location);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/locations/{id}").buildAndExpand(location.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
