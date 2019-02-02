package com.example.nekretnine.Controller;

import com.example.nekretnine.Messages.CustomErrorType;
import com.example.nekretnine.Model.Advert;
import com.example.nekretnine.Repository.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/adverts")
public class AdvertController {
    private final AdvertRepository advertRepository;

    @Autowired
    public AdvertController(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    // -------------------Retrieve All Adverts---------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Iterable<Advert>> listAllAdverts() {
        Iterable<Advert> adverts = advertRepository.findAll();
        if (adverts.spliterator().getExactSizeIfKnown() < 1) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Iterable<Advert>>(adverts, HttpStatus.OK);
    }


    // -------------------Retrieve One Advert---------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/{advertId}")
    ResponseEntity<?> getAdvert (@PathVariable Long advertId) {

        Optional<Advert> advert = this.advertRepository.findById(advertId);
        if (!advert.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Advert with id " + advertId
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Advert>>(advert, HttpStatus.OK);
    }
}
