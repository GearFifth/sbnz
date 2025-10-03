package com.ftn.sbnz.model.models;

import com.ftn.sbnz.model.enums.FitnessLevel;
import com.ftn.sbnz.model.enums.PublicTransportAccessibility;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "tags")
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name; // Naziv lokacije

    @Column(nullable = false)
    private String type; // Tip lokacije (jezero, pećina, grad...)

    @Column(nullable = false)
    private String region; // Region kojem pripada

    private double ticketPrice; // Cena ulaznice

    private int visitTimeMinutes; // Procenjeno vreme za obilazak u minutima

    @Enumerated(EnumType.STRING)
    private FitnessLevel requiredFitness; // Zahtevana fizička sprema

    @Enumerated(EnumType.STRING)
    private PublicTransportAccessibility publicTransportAccessibility;

    // NOVO: Polje za potrebnu opremu/odeću
    private String requiredEquipment;

    // NOVO: Polje za dodatni opis (npr. struktura cene na Bledu)
    @Column(length = 1024)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "location_tags",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    // Sezonski atributi
    private boolean seasonal; // Da li je lokacija sezonska
    private int openingMonth; // Početni mesec rada
    private int closingMonth; // Krajnji mesec rada
}