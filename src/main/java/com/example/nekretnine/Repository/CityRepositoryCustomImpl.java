package com.example.nekretnine.Repository;

import com.example.nekretnine.Model.City;
import com.example.nekretnine.Model.Location;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CityRepositoryCustomImpl implements CityRepositoryCustom{
    @PersistenceContext
    EntityManager entityManager;
    public List<City> findByCityId(Long id){
        Query query = entityManager.createNativeQuery("select * from city where id=" + id, City.class);
        System.out.println("City...");
        System.out.println(query.getResultList().get(0).toString());
        return query.getResultList();
    }
}
