package com.example.nekretnine.Repository;

import com.example.nekretnine.Model.Location;

import java.util.List;

public interface LocationRepositoryCustom {

    List<Location> findBySettlementName(String name);
}
