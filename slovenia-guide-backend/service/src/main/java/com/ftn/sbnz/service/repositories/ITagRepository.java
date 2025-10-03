package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ITagRepository extends JpaRepository<Tag, UUID> {
    // Metoda za pronalazak taga po imenu da se izbegnu duplikati
    Optional<Tag> findByName(String name);
}