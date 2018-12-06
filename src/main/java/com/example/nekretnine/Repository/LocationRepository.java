package com.example.nekretnine.Repository;

import com.example.nekretnine.Model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
