package com.example.BSS.repository;

import com.example.BSS.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    // List<Venue> findByNameContainingIgnoreCase(String name);
}