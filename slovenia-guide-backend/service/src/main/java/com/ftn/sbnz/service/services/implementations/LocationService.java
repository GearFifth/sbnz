package com.ftn.sbnz.service.services.implementations;

import com.ftn.sbnz.model.dtos.location.CreateLocationRequest;
import com.ftn.sbnz.model.models.Location;
import com.ftn.sbnz.model.models.Tag;
import com.ftn.sbnz.service.exceptions.EntityNotFoundException;
import com.ftn.sbnz.service.repositories.ILocationRepository;
import com.ftn.sbnz.service.repositories.ITagRepository;
import com.ftn.sbnz.service.services.ILocationService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService implements ILocationService {

    private final ILocationRepository locationRepository;
    private final ITagRepository tagRepository;
    private final ModelMapper mapper;

    @Override
    public Location create(CreateLocationRequest dto) {
        if (locationRepository.findByName(dto.getName()).isPresent()) {
            throw new EntityExistsException("Location with name '" + dto.getName() + "' already exists.");
        }

        Location location = mapper.map(dto, Location.class);

        List<Tag> tags = dto.getTags().stream().map(tagName ->
                tagRepository.findByName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(null, tagName)))
        ).collect(Collectors.toList());
        location.setTags(tags);

        return locationRepository.save(location);
    }

    @Override
    public Location findById(UUID id) {
        return locationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Location with ID " + id + " not found."));
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }
}