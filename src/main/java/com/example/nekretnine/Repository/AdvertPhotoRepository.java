package com.example.nekretnine.Repository;

import com.example.nekretnine.Model.Advert;
import com.example.nekretnine.Model.AdvertPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Transactional
public interface AdvertPhotoRepository extends JpaRepository<AdvertPhoto, Long> {
    long deleteByAdvertId(long advertId);

    ArrayList<AdvertPhoto> findAllByAdvertId(long advertId);
    AdvertPhoto findFirstByAdvertId(Long advertId);

}
