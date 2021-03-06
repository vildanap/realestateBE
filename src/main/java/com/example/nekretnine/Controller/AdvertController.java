package com.example.nekretnine.Controller;

import com.example.nekretnine.Messages.CustomErrorType;
import com.example.nekretnine.Model.*;
import com.example.nekretnine.Repository.*;
import com.example.nekretnine.Service.FileService;
import com.example.nekretnine.Service.LocationService;
import com.example.nekretnine.Service.RegisteredUserService;

import com.sun.net.httpserver.Authenticator;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adverts")
public class AdvertController {
    @Autowired
    RegisteredUserService registeredUserService;
    @Autowired
    LocationService locationService;
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
    ResponseEntity<?> getAdvert(@PathVariable Long advertId) {

        Optional<Advert> advert = this.advertRepository.findById(advertId);
        if (!advert.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Advert with id " + advertId
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Advert>>(advert, HttpStatus.OK);
    }

    //Get users posted adverts
    // -------------------Retrieve All Adverts---------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/getPostedBy/{userId}")
    public ResponseEntity<Iterable<Advert>> listAllpostedByUserAdverts(@PathVariable("userId") long userId) {
        Iterable<Advert> adverts = this.advertRepository.findByUserId(userId);
        if (adverts.spliterator().getExactSizeIfKnown() < 1) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Iterable<Advert>>(adverts, HttpStatus.OK);
    }

    // -------------------Create an Advert --------------------------------------------------
    @PostMapping("/post")
    public ResponseEntity createAdvert(@RequestParam(value = "files", required = false) MultipartFile[] files,
                                       @RequestParam("formDataJson") String formDataJson) throws IOException, JSONException, ServletException {
        JSONObject jsonObject = new JSONObject(formDataJson);
        Advert advert = new Advert();

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
        String settlement = jsonObject.getString("location");
        System.out.println(settlement);

        User user = registeredUserService.findById(userId);
        if (user == null) {
            return new ResponseEntity(new CustomErrorType("Unable to create advert. User with id " + userId + " not found."),
                    HttpStatus.NOT_FOUND);
        } else {
            advert.setUser(user);
        }

        Location location = locationService.findBySettlement(settlement);
        if (location == null) {
            return new ResponseEntity(new CustomErrorType("Unable to create advert. Settlement with name " + settlement + " not found."),
                    HttpStatus.NOT_FOUND);
        } else {
            advert.setLocation(location);
        }

        //save advert
        Advert savedAdvert = advertRepository.save(advert);

        addAdvertPhotos(savedAdvert.getId(), files);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    // -------------------Retrieve Favorite Adverts---------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/favorite/{userId}")
    public ResponseEntity<Iterable<Advert>> listAllFavoriteAdverts(@PathVariable Long userId) {

        Iterable<UserAdvert> userAdverts = userAdvertRepository.findAllByUserId(userId);

        System.out.println(userId);
        List<Advert> adverts = new ArrayList<>();

        for (UserAdvert userAdvert : userAdverts) {
            adverts.add(advertRepository.findById(userAdvert.getAdvertId()).get());
        }

        if (adverts.spliterator().getExactSizeIfKnown() < 1) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Iterable<Advert>>(adverts, HttpStatus.OK);
    }

    // -------------------Get Adverts - Sale ---------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/sale")
    public ResponseEntity<Iterable<Advert>> listAllAdvertsForSale() {
        Iterable<Advert> adverts = advertRepository.findAllByAdvertType("Sale");
        if (adverts.spliterator().getExactSizeIfKnown() < 1) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Iterable<Advert>>(adverts, HttpStatus.OK);
    }

    // -------------------Get Adverts - Rent ---------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/rent")
    public ResponseEntity<Iterable<Advert>> listAllAdvertsForRent() {
        Iterable<Advert> adverts = advertRepository.findAllByAdvertType("Rent");
        if (adverts.spliterator().getExactSizeIfKnown() < 1) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Iterable<Advert>>(adverts, HttpStatus.OK);
    }

    // -------------------Add favorite Advert --------------------------------------------------
    @RequestMapping(method = RequestMethod.POST, value = "/post/favorite/{userId}/{advertId}")
    public ResponseEntity addFavoriteAdvert(@PathVariable Long userId, @PathVariable Long advertId) {
        UserAdvert userAdvert = new UserAdvert();
        userAdvert.setAdvertId(advertId);
        userAdvert.setUserId(userId);

        if(!userAdvertRepository.existsUserAdvertByAdvertIdAndUserId(advertId, userId)) {
            userAdvertRepository.save(userAdvert);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    // -------------------Remove favorite Advert --------------------------------------------------
    @RequestMapping(method = RequestMethod.DELETE, value = "/favorite/{userId}/{advertId}")
    public ResponseEntity removeFavoriteAdvert(@PathVariable Long userId, @PathVariable Long advertId) {
        UserAdvert userAdvert = userAdvertRepository.findByAdvertIdAndUserId(advertId, userId);
        if(userAdvert != null) {
            userAdvertRepository.deleteById(userAdvert.getId());
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    // -------------------Check for favorite Advert --------------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/favorite/{userId}/{advertId}")
    public ResponseEntity checkIsItFavorite(@PathVariable Long userId, @PathVariable Long advertId) {

        if(userAdvertRepository.existsUserAdvertByAdvertIdAndUserId(advertId, userId)){
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // -------------------Update Views Count---------------------------------------------
    @RequestMapping(value = "/plusView/{advertId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("advertId") Long advertId) throws ServletException {

        Advert advert = advertRepository.findById(advertId).get();

        if (advert == null) {
            return new ResponseEntity(new CustomErrorType("Unable to update. Advert with id " + advertId + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        advert.setViewsCount(advert.getViewsCount() + 1);
        advertRepository.save(advert);

        return new ResponseEntity<Advert>(advert, HttpStatus.OK);
    }

    // -------------------Edit Advert --------------------------------------------------
    @PutMapping("/{advertId}")
    public ResponseEntity editAdvert(@PathVariable("advertId") Long advertId, @RequestParam(value = "files", required = false) MultipartFile[] files,
                                     @RequestParam("formDataJson") String formDataJson) throws IOException, JSONException, ServletException {
        JSONObject jsonObject = new JSONObject(formDataJson);

        Advert editedAdvert = getAdvertFromDataJson(formDataJson);

        long userId = jsonObject.getLong("userId");
        String settlement = jsonObject.getString("location");

        User user = registeredUserService.findById(userId);
        if (user==null) {
            return new ResponseEntity(new CustomErrorType("Unable to update advert. User with id " + userId + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        else {
            editedAdvert.setUser(user);
        }

        Location location = locationService.findBySettlement(settlement);
        if (location==null) {
            return new ResponseEntity(new CustomErrorType("Unable to update advert. Settlement with name " + settlement + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        else{
            editedAdvert.setLocation(location);
        }

        Advert advert = advertRepository.findById(advertId).get();

        if (advert == null) {
            return new ResponseEntity(new CustomErrorType("Unable to update. Advert with id " + editedAdvert.getId() + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        //remove all photos
        if(files != null){
            advertPhotoRepository.deleteByAdvertId(advertId);
        }

        //add new photos
        addAdvertPhotos(advertId, files);

        //edit advert
        advert.setTitle(editedAdvert.getTitle());
        advert.setDescription(editedAdvert.getDescription());
        advert.setAdvertType(editedAdvert.getAdvertType());
        advert.setPropertyType(editedAdvert.getPropertyType());
        advert.setPrice(editedAdvert.getPrice());
        advert.setArea(editedAdvert.getArea());
        advert.setAddress(editedAdvert.getAddress());
        advert.setViewsCount(editedAdvert.getViewsCount());
        advert.setNumberOfRooms(editedAdvert.getNumberOfRooms());
        advert.setLocation(editedAdvert.getLocation());
        advertRepository.save(advert);

        return new ResponseEntity<Advert>(advert, HttpStatus.OK);
    }

    // -------------------Delete advert---------------------------------------------
    @RequestMapping(method = RequestMethod.DELETE, value = "/{advertId}")
    public ResponseEntity<?> delete(@PathVariable("advertId") Long advertId) {
        System.out.println(advertId);
        Optional<Advert> advert = advertRepository.findById(advertId);

        if (!advert.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            //delete photos first
            advertPhotoRepository.deleteByAdvertId(advertId);

            //delete favourite
            userAdvertRepository.deleteByAdvertId(advertId);

            //delete advert
            advertRepository.deleteById(advertId);
            return new ResponseEntity<Advert>(HttpStatus.OK);
        }

    }

    // -------------------Search Adverts---------------------------------------------
    @RequestMapping(method = RequestMethod.POST, value = "/search")
    public ResponseEntity<Iterable<Advert>> searchAdverts(@RequestBody SearchAdvert searchAdvert) {
        Iterable<Advert> adverts;

        if(searchAdvert.getLocationId() != 0){
            if (searchAdvert.getAdvertType() == 1) {
                adverts = advertRepository.findAllByAdvertTypeAndLocationIdAndNumberOfRooms("Rent", searchAdvert.getLocationId(), searchAdvert.getNumberOfRooms());
            } else if (searchAdvert.getAdvertType() == 2) {
                adverts = advertRepository.findAllByAdvertTypeAndLocationIdAndNumberOfRooms("Sale", searchAdvert.getLocationId(), searchAdvert.getNumberOfRooms());
            } else {
                adverts = advertRepository.findAllByLocationIdAndNumberOfRooms(searchAdvert.getLocationId(), searchAdvert.getNumberOfRooms());
            }
        }
        else if(searchAdvert.getCityId() != 0 && searchAdvert.getLocationId() == 0){
            if (searchAdvert.getAdvertType() == 1) {
                adverts = advertRepository.findAllByAdvertTypeAndCityIdAndNumberOfRooms("Rent", searchAdvert.getCityId(), searchAdvert.getNumberOfRooms());
            } else if (searchAdvert.getAdvertType() == 2) {
                adverts = advertRepository.findAllByAdvertTypeAndCityIdAndNumberOfRooms("Sale", searchAdvert.getCityId(), searchAdvert.getNumberOfRooms());
            } else {
                adverts = advertRepository.findAllByCityIdAndNumberOfRooms(searchAdvert.getCityId(), searchAdvert.getNumberOfRooms());
            }
        }
        else{
            if (searchAdvert.getAdvertType() == 1) {
                adverts = advertRepository.findAllByAdvertTypeAndNumberOfRooms("Rent", searchAdvert.getNumberOfRooms());
            } else if (searchAdvert.getAdvertType() == 2) {
                adverts = advertRepository.findAllByAdvertTypeAndNumberOfRooms("Sale", searchAdvert.getNumberOfRooms());
            } else {
                adverts = advertRepository.findAllByNumberOfRooms(searchAdvert.getNumberOfRooms());
            }
        }

        Iterator<Advert> it = adverts.iterator();
        Advert advert;
        while (it.hasNext()) {
            advert = it.next();
            if (advert.getPrice() < searchAdvert.getMinPrice() || advert.getPrice() > searchAdvert.getMaxPrice()) {
                it.remove();
            }
        }

        if (adverts.spliterator().getExactSizeIfKnown() < 1) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Iterable<Advert>>(adverts, HttpStatus.OK);
    }

    // -------------------Get User Information---------------------------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/userInfo/{userId}")
    public ResponseEntity<UserAdvertInfo> getUserAdvertInformation(@PathVariable Long userId) {
        UserAdvertInfo userAdvertInfo = new UserAdvertInfo();

        userAdvertInfo.setNumberOfAdverts(advertRepository.countAdvertByUserId(userId));
        userAdvertInfo.setNumberOfAdvertViews(advertRepository.getSumOfAdvertViews(userId) != null ? advertRepository.getSumOfAdvertViews(userId) : 0);
        userAdvertInfo.setNumberOfFavorites(userAdvertRepository.countUserAdvertByUserId(userId));

        return new ResponseEntity<UserAdvertInfo>(userAdvertInfo, HttpStatus.OK);
    }

    public Advert getAdvertFromDataJson(String formDataJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(formDataJson);
        Advert advert = new Advert();

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

        return advert;
    }

    public void addAdvertPhotos(long advertId, MultipartFile[] files) throws IOException {
        AdvertPhoto advertPhoto = new AdvertPhoto();
        if (files != null) {
            for (MultipartFile uploadedFile : files) {
                long id = fileService.save(uploadedFile);
                advertPhoto = new AdvertPhoto();
                advertPhoto.setAdvertId(advertId);
                advertPhoto.setFileId(id);
                advertPhotoRepository.save(advertPhoto);

                // Creating the directory to store file
                //String rootPath = System.getProperty("catalina.home");
                java.io.File dir = new java.io.File(java.io.File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                java.io.File serverFile = new java.io.File(dir.getAbsolutePath()+ java.io.File.separator + uploadedFile.getOriginalFilename());
                System.out.println(uploadedFile.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(uploadedFile.getBytes());
                stream.close();
                System.out.println("Server File Location="
                        + serverFile.getAbsolutePath());

            }
        }
    }
}