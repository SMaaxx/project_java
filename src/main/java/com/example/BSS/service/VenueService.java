package com.example.BSS.service;

import com.example.BSS.entity.Venue;
import com.example.BSS.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VenueService {
    private final VenueRepository venueRepository;

    public List<Venue> findAll() {
        return venueRepository.findAll();
    }

    public Optional<Venue> findById(Long id) {
        return venueRepository.findById(id);
    }

    @Transactional
    public Venue save(Venue venue) {
        return venueRepository.save(venue);
    }

    @Transactional
    public void deleteById(Long id) {
        venueRepository.deleteById(id);
    }
}