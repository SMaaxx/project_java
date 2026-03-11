package com.example.BSS.controller;

import com.example.BSS.service.EventRegistrationService;
import com.example.BSS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final EventRegistrationService registrationService;
    private final UserService userService;

    @GetMapping("/event/{eventId}/add")
    public String addRegistrationForm(@PathVariable Long eventId, Model model) {
        model.addAttribute("eventId", eventId);
        model.addAttribute("users", userService.findUsers());
        return "registrations/add";
    }

    @PostMapping("/event/{eventId}")
    public String addRegistration(@PathVariable Long eventId,
                                  @RequestParam Long userId,
                                  RedirectAttributes redirectAttributes) {
        try {
            registrationService.registerUser(eventId, userId);
            redirectAttributes.addFlashAttribute("successMessage", "Участник успешно добавлен");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/events/" + eventId + "/registrations";
    }

    @PostMapping("/event/{eventId}/user/{userId}/delete")
    public String deleteRegistration(@PathVariable Long eventId,
                                     @PathVariable Long userId,
                                     RedirectAttributes redirectAttributes) {
        registrationService.unregisterUser(eventId, userId);
        redirectAttributes.addFlashAttribute("successMessage", "Регистрация удалена");
        return "redirect:/events/" + eventId + "/registrations";
    }
}