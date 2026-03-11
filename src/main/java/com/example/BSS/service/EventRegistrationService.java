package com.example.BSS.service;

import com.example.BSS.entity.Event;
import com.example.BSS.entity.EventRegistration;
import com.example.BSS.entity.EventRegistrationId;
import com.example.BSS.entity.User;
import com.example.BSS.entity.Venue;
import com.example.BSS.repository.EventRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventRegistrationService {
    private final EventRegistrationRepository registrationRepository;
    private final EventService eventService;
    private final UserService userService;  // используем UserService, а не репозиторий

    public long countRegistrationsByEvent(Long eventId) {
        return registrationRepository.countByEventId(eventId);
    }

    @Transactional
    public void registerUser(Long eventId, Long userId) {
        if (registrationRepository.existsByEventIdAndUserId(eventId, userId)) {
            throw new IllegalStateException("User already registered for this event");
        }

        Event event = eventService.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Venue venue = event.getVenue();
        if (venue != null && venue.getCapacity() != null) {
            long currentRegistrations = countRegistrationsByEvent(eventId);
            if (currentRegistrations >= venue.getCapacity()) {
                throw new IllegalStateException("Event venue capacity exceeded (max: " + venue.getCapacity() + ")");
            }
        }

        User user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        EventRegistrationId id = new EventRegistrationId(userId, eventId);
        EventRegistration registration = new EventRegistration();
        registration.setId(id);
        registration.setEvent(event);
        registration.setUser(user);
        registration.setRegistrationDate(LocalDateTime.now());

        registrationRepository.save(registration);
    }

    @Transactional
    public void unregisterUser(Long eventId, Long userId) {
        registrationRepository.deleteByEventIdAndUserId(eventId, userId);
    }
}