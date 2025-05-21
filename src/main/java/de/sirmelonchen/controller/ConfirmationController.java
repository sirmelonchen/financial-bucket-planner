package de.sirmelonchen.controller;

import de.sirmelonchen.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConfirmationController {

    private final UserService userService;

    public ConfirmationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token, Model model) {
        try {
            userService.confirmUser(token);
            model.addAttribute("message", "E-Mail bestätigt. Du kannst dich jetzt einloggen.");
        } catch (Exception e) {
            model.addAttribute("message", "Bestätigung fehlgeschlagen: " + e.getMessage());
        }
        return "confirmation-result"; // Ein einfaches HTML-Template
    }
}
