package com.example.nekretnine.Service;

import com.example.nekretnine.Model.Location;
import com.example.nekretnine.Model.User;
import com.example.nekretnine.Repository.LocationRepository;
import com.example.nekretnine.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location findBySettlement (String name) throws ServletException {
        System.out.println(name);
        try {
            List<Location> location = locationRepository.findBySettlementName(name);
            if(!location.isEmpty()) {  System.out.println("lista");
                return location.get(0);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServletException("LocationService find by settlement exception");
        }
        return null;
    }
}
