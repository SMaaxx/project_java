package com.example.BSS.controller;

import com.example.BSS.entity.Venue;
import com.example.BSS.service.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("venues", venueService.findAll());
        return "venues/list";
    }

    @GetMapping("/new")
    public String newVenue(Model model) {
        model.addAttribute("venue", new Venue());
        return "venues/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("venue") Venue venue,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            return "venues/form";
        }
        venueService.save(venue);
        return "redirect:/venues";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Venue venue = venueService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid venue Id:" + id));
        model.addAttribute("venue", venue);
        return "venues/form";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("venue") Venue venue,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            return "venues/form";
        }
        venue.setId(id);
        venueService.save(venue);
        return "redirect:/venues";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        venueService.deleteById(id);
        return "redirect:/venues";
    }
}