package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.model.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRouteRepository extends JpaRepository<Route, UUID> {
    // Metoda za pronalazak rute izmedju dve specifične lokacije
    Optional<Route> findByLocationAAndLocationB(Location locationA, Location locationB);
}