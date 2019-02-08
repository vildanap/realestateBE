package com.example.nekretnine;

import com.example.nekretnine.Model.City;
import com.example.nekretnine.Model.Location;
import com.example.nekretnine.Repository.CityRepository;
import com.example.nekretnine.Repository.LocationRepository;
import com.example.nekretnine.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {

    private LocationRepository locationRepository;
    private CityRepository cityRepository;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseSeeder(
            LocationRepository locationRepository,
            CityRepository cityRepository,
            JdbcTemplate jdbcTemplate) {
        System.out.println("Constructor");
        this.locationRepository=locationRepository;
        this.cityRepository=cityRepository;
        this.jdbcTemplate = jdbcTemplate;
    }
    @EventListener
    public void seed(ContextRefreshedEvent event) {
        System.out.println("EVENT");

        if(cityRepository.count() == 0)
            cityTableSeed();

        if(locationRepository.count() == 0)
            locationTableSeed();
    }

    private void cityTableSeed() {
        try {
            City c = new City();
            c.setName("Sarajevo");
            c = cityRepository.save(c);

            c = new City();
            c.setName("Tuzla");
            c = cityRepository.save(c);

            c = new City();
            c.setName("Mostar");
            c = cityRepository.save(c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void locationTableSeed() {
        try {

            Location l = new Location();
            l.setSettlement("Pofalici");
            l.setCity(cityRepository.findByCityId(Long.valueOf(1)).get(0));
            l=locationRepository.save(l);
            l = new Location();
            l.setSettlement("Ciglane");
            l.setCity(cityRepository.findByCityId(Long.valueOf(1)).get(0));
            l=locationRepository.save(l);
            l = new Location();
            l.setSettlement("Grbavica");
            l.setCity(cityRepository.findByCityId(Long.valueOf(1)).get(0));
            l=locationRepository.save(l);

            l = new Location();
            l.setSettlement("Mosnik");
            l.setCity(cityRepository.findByCityId(Long.valueOf(2)).get(0));
            l=locationRepository.save(l);
            l = new Location();
            l.setSettlement("Slatina");
            l.setCity(cityRepository.findByCityId(Long.valueOf(2)).get(0));
            l=locationRepository.save(l);
            l = new Location();
            l.setSettlement("Miladije");
            l.setCity(cityRepository.findByCityId(Long.valueOf(2)).get(0));
            l=locationRepository.save(l);

            l = new Location();
            l.setSettlement("Brankovac");
            l.setCity(cityRepository.findByCityId(Long.valueOf(3)).get(0));
            l=locationRepository.save(l);
            l = new Location();
            l.setSettlement("Bijeli Brijeg");
            l.setCity(cityRepository.findByCityId(Long.valueOf(3)).get(0));
            l=locationRepository.save(l);
            l = new Location();
            l.setSettlement("Zalik");
            l.setCity(cityRepository.findByCityId(Long.valueOf(3)).get(0));
            l=locationRepository.save(l);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
