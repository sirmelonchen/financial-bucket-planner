package de.sirmelonchen.controller;

import de.sirmelonchen.model.Workspace;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class UserHomeController {

    // Map von Username zu Liste von Workspaces (in-memory, zur Demo)
    private Map<String, List<Workspace>> userWorkspaces = new HashMap<>();

    @GetMapping("/home")
    public String userHome(@AuthenticationPrincipal UserDetails user, Model model) {
        String username = user.getUsername();
        model.addAttribute("username", username);

        List<Workspace> workspaces = userWorkspaces.getOrDefault(username, new ArrayList<>());
        model.addAttribute("workspaces", workspaces);

        return "user-home";
    }

    @PostMapping("/workspaces")
    public String addWorkspace(@AuthenticationPrincipal UserDetails user,
                               @RequestParam String name) {
        String username = user.getUsername();
        userWorkspaces.computeIfAbsent(username, k -> new ArrayList<>())
                .add(new Workspace(name));
        return "redirect:/home";
    }
}
