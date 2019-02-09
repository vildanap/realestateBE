package com.example.nekretnine.Repository;

import com.example.nekretnine.Model.Advert;
import com.example.nekretnine.Model.City;
import com.example.nekretnine.Model.UserAdvert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    Iterable<Advert> findAllByAdvertType(String advertType);

    Iterable<Advert> findByUserId(long userId);

    Iterable<Advert> findAllByAdvertTypeAndLocationId(String advertType, long locationId);

    Iterable<Advert> findAllByLocationId(long locationId);

    Iterable<Advert> findAllByAdvertTypeAndLocationIdAndNumberOfRooms(String advertType, long locationId, long numberOfRooms);

    Iterable<Advert> findAllByLocationIdAndNumberOfRooms(long locationId, long numberOfRooms);
}
