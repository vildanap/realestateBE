package com.example.nekretnine.Repository;

import com.example.nekretnine.Model.Location;
import com.example.nekretnine.Model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class LocationRepositoryCustomImpl implements LocationRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    public List<Location> findBySettlementName(String name){
        Query query = entityManager.createNativeQuery("select * from location where settlement='" + name + "'", Location.class);
        System.out.println("Settlement...");
        System.out.println(query.getResultList().get(0).toString());
        return query.getResultList();
    }
}
