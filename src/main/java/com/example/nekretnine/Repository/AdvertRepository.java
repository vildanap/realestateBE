package com.example.nekretnine.Repository;

import com.example.nekretnine.Model.Advert;
import com.example.nekretnine.Model.City;
import com.example.nekretnine.Model.UserAdvert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    Iterable<Advert> findAllByAdvertType(String advertType);

    Iterable<Advert> findByUserId(long userId);

    Iterable<Advert> findAllByAdvertTypeAndLocationId(String advertType, long locationId);

    Iterable<Advert> findAllByLocationId(long locationId);

    Iterable<Advert> findAllByAdvertTypeAndLocationIdAndNumberOfRooms(String advertType, long locationId, long numberOfRooms);

    Iterable<Advert> findAllByLocationIdAndNumberOfRooms(long locationId, long numberOfRooms);

    Iterable<Advert> findAllByAdvertTypeAndNumberOfRooms(String advertType, long numberOfRooms);

    Iterable<Advert> findAllByNumberOfRooms(long numberOfRooms);

    @Query("SELECT a FROM Advert a INNER JOIN Location loc ON a.location.id = loc.id WHERE a.advertType = :advertType AND loc.city.id = :cityId AND a.numberOfRooms = :numberOfRooms")
    Iterable<Advert> findAllByAdvertTypeAndCityIdAndNumberOfRooms(@Param("advertType") String advertType, @Param("cityId") long cityId, @Param("numberOfRooms") long numberOfRooms);

    @Query("SELECT a FROM Advert a INNER JOIN Location loc ON a.location.id = loc.id WHERE loc.city.id = :cityId AND a.numberOfRooms = :numberOfRooms")
    Iterable<Advert> findAllByCityIdAndNumberOfRooms(@Param("cityId") long cityId, @Param("numberOfRooms") long numberOfRooms);

    Long countAdvertByUserId(long userId);

    @Query("SELECT SUM(a.viewsCount) FROM Advert a WHERE a.user.id = :userId")
    Long getSumOfAdvertViews(@Param("userId") Long userId);

    Long deleteByUserId(Long id);
}
