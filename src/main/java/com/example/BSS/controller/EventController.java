package com.example.BSS.controller;

import com.example.BSS.entity.Event;
import com.example.BSS.service.EventService;
import com.example.BSS.service.UserService;
import com.example.BSS.service.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final VenueService venueService;
    private final UserService userService;

    @GetMapping
    public String list(@RequestParam(required = false) String fromDate,
                       @RequestParam(required = false) String toDate,
                       Model model) {
        LocalDateTime from = null;
        LocalDateTime to = null;

        try {
            if (fromDate != null && !fromDate.isEmpty()) {
                LocalDate date = LocalDate.parse(fromDate);
                from = date.atStartOfDay();
            }
            if (toDate != null && !toDate.isEmpty()) {
                LocalDate date = LocalDate.parse(toDate);
                to = date.atTime(LocalTime.MAX);
            }
        } catch (DateTimeParseException ignored) {}

        model.addAttribute("events", eventService.findFiltered(from, to));
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        return "events/list";
    }

    @GetMapping("/new")
    public String newEvent(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("venues", venueService.findAll());
        model.addAttribute("organizers", userService.findOrganizers());
        return "events/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("event") Event event,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("venues", venueService.findAll());
            model.addAttribute("organizers", userService.findOrganizers());
            return "events/form";
        }
        eventService.save(event);
        return "redirect:/events";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Event event = eventService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event Id:" + id));
        model.addAttribute("event", event);
        model.addAttribute("venues", venueService.findAll());
        model.addAttribute("organizers", userService.findOrganizers());
        return "events/form";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("event") Event event,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("venues", venueService.findAll());
            model.addAttribute("organizers", userService.findOrganizers());
            return "events/form";
        }
        event.setId(id);
        eventService.save(event);
        return "redirect:/events";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        eventService.deleteById(id);
        return "redirect:/events";
    }

    @GetMapping("/{id}/registrations")
    public String registrations(@PathVariable Long id, Model model) {
        Event event = eventService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event Id:" + id));
        model.addAttribute("event", event);
        model.addAttribute("registrations", event.getRegistrations());
        return "events/registrations";
    }
}