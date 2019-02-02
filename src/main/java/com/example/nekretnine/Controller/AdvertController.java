package com.example.nekretnine.Controller;

import com.example.nekretnine.Messages.CustomErrorType;
import com.example.nekretnine.Model.Advert;
import com.example.nekretnine.Model.AdvertPhoto;
import com.example.nekretnine.Repository.AdvertPhotoRepository;
import com.example.nekretnine.Repository.AdvertRepository;
import com.example.nekretnine.Service.FileService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/adverts")
public class AdvertController {
    private final AdvertRepository advertRepository;
    private final AdvertPhotoRepository advertPhotoRepository;
    private FileService fileService;

    @Autowired
    public AdvertController(AdvertRepository advertRepository, FileService fileService, AdvertPhotoRepository advertPhotoRepository) {
        this.fileService = fileService;
        this.advertRepository = advertRepository;
        this.advertPhotoRepository = advertPhotoRepository;
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


    // -------------------Create an Advert --------------------------------------------------
    @PostMapping("/post")
    public ResponseEntity<Object> createAdvert(@RequestParam(value = "files", required = false) MultipartFile[] files,
                                             @RequestParam("formDataJson") String formDataJson) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject(formDataJson);
        Advert advert = new Advert();
        AdvertPhoto advertPhoto = new AdvertPhoto();
        advert.setTitle(jsonObject.getString("title"));
        //Advert savedAdvert = advertRepository.save(advert);
        //hardcoded dok ne skontam dalje :P
        advertPhoto.setAdvertId(1);
        for(MultipartFile uploadedFile : files) {
            long id = fileService.save(uploadedFile);
            advertPhoto.setFileId(id);
            advertPhotoRepository.save(advertPhoto);
        }
        return ResponseEntity.noContent().build();
    }

}
