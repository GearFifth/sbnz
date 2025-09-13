package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ILocationRepository extends JpaRepository<Location, UUID> {
    // Metoda za pronalazak lokacije po imenu, korisno za validaciju
    Optional<Location> findByName(String name);
}