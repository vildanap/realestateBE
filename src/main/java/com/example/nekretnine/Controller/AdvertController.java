package com.example.nekretnine.Controller;

import com.example.nekretnine.Messages.CustomErrorType;
import com.example.nekretnine.Model.Advert;
import com.example.nekretnine.Model.AdvertPhoto;
import com.example.nekretnine.Model.Location;
import com.example.nekretnine.Model.UserAdvert;
import com.example.nekretnine.Repository.*;
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

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adverts")
public class AdvertController {
    private final AdvertRepository advertRepository;
    private final AdvertPhotoRepository advertPhotoRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final UserAdvertRepository userAdvertRepository;
    private FileService fileService;

    @Autowired
    public AdvertController(AdvertRepository advertRepository,
                            FileService fileService,
                            AdvertPhotoRepository advertPhotoRepository,
                            UserRepository userRepository,
                            LocationRepository locationRepository,
                            UserAdvertRepository userAdvertRepository
    ) {
        this.fileService = fileService;
        this.advertRepository = advertRepository;
        this.advertPhotoRepository = advertPhotoRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.userAdvertRepository = userAdvertRepository;
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
    public ResponseEntity createAdvert(@RequestParam(value = "files", required = false) MultipartFile[] files,
                                             @RequestParam("formDataJson") String formDataJson) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject(formDataJson);
        Advert advert = new Advert();
        AdvertPhoto advertPhoto = new AdvertPhoto();

        //get advert data
        advert.setTitle(jsonObject.getString("title"));
        advert.setDescription(jsonObject.getString("description"));
        advert.setAdvertType(jsonObject.getString("advertType"));
        advert.setPropertyType(jsonObject.getString("propertyType"));
        advert.setPrice(jsonObject.getDouble("price"));
        advert.setArea(jsonObject.getDouble("area"));
        advert.setAddress(jsonObject.getString("address"));
        advert.setViewsCount(jsonObject.getInt("viewsCount"));
        advert.setNumberOfRooms(jsonObject.getInt("numberOfRooms"));

        long userId = jsonObject.getLong("userId");
        long locationId = jsonObject.getLong("locationId");

        advert.setUser(userRepository.findById(userId).get());
        advert.setLocation(locationRepository.findById(locationId).get());

        //save advert
        Advert savedAdvert = advertRepository.save(advert);

        advertPhoto.setAdvertId(savedAdvert.getId());

        if(files != null){
            for(MultipartFile uploadedFile : files) {
                long id = fileService.save(uploadedFile);
                advertPhoto.setFileId(id);
                advertPhotoRepository.save(advertPhoto);
            }
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    // -------------------Retrieve Favorite Adverts---------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/favorite/{userId}")
    public ResponseEntity<Iterable<Advert>> listAllFavoriteAdverts(@PathVariable Long userId) {
        Iterable<UserAdvert> userAdverts = userAdvertRepository.findAllByUserId(userId);

        List<Advert> adverts = new ArrayList<>();

        for(UserAdvert userAdvert : userAdverts){
            adverts.add(advertRepository.findById(userAdvert.getAdvertId()).get());
        }

        if (adverts.spliterator().getExactSizeIfKnown() < 1) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Iterable<Advert>>(adverts, HttpStatus.OK);
    }

    // -------------------Add favorite Advert --------------------------------------------------
    @RequestMapping(method = RequestMethod.POST, value="/post/favorite/{userId}/{advertId}")
    public ResponseEntity addFavoriteAdvert(@PathVariable Long userId, @PathVariable Long advertId) {
        UserAdvert userAdvert = new UserAdvert();
        userAdvert.setAdvertId(advertId);
        userAdvert.setUserId(userId);

        userAdvertRepository.save(userAdvert);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    // -------------------Update Views Count---------------------------------------------
    @RequestMapping(value = "/plusView/{advertId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("advertId") Long advertId) throws ServletException {

        Advert advert = advertRepository.findById(advertId).get();

        if (advert==null) {
            return new ResponseEntity(new CustomErrorType("Unable to update. Advert with id " + advertId + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        advert.setViewsCount(advert.getViewsCount()+1);
        advertRepository.save(advert);

        return new ResponseEntity<Advert>(advert, HttpStatus.OK);
    }
}
