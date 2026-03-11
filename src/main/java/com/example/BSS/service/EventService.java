package com.example.BSS.service;

import com.example.BSS.entity.Event;
import com.example.BSS.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {
    private final EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> findFiltered(LocalDateTime from, LocalDateTime to) {
        if (from == null && to == null) {
            return findAll();
        }

        LocalDateTime start = from != null ? from : LocalDateTime.MIN;
        LocalDateTime end = to != null ? to : LocalDateTime.MAX;
        return eventRepository.findByEventDateBetween(start, end);
    }

    @Transactional
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Transactional
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }
}