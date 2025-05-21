package de.sirmelonchen.controller;

import de.sirmelonchen.model.Workspace;
import de.sirmelonchen.service.BucketService;
import de.sirmelonchen.service.UserService;
import de.sirmelonchen.service.WorkspaceService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;


@Controller
public class UserController {

    private final UserService userService;
    private final Map<String, List<Workspace>> userWorkspaces = new HashMap<>();
    private final WorkspaceService workspaceService;
    private final BucketService bucketService;

    public UserController(UserService userService, WorkspaceService workspaceService, BucketService bucketService) {
        this.userService = userService;
        this.workspaceService = workspaceService;
        this.bucketService = bucketService;
    }


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/home";
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String email,Model model) {
        userService.registerUser(username, password, email);
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String userHome(@AuthenticationPrincipal UserDetails user, Model model) {
        String username = user.getUsername();
        model.addAttribute("username", username);

        List<Workspace> workspaces = workspaceService.getWorkspacesForUser(username);
        model.addAttribute("workspaces", workspaces);

        return "user-home";
    }

    @PostMapping("/workspaces")
    public String addWorkspace(@AuthenticationPrincipal UserDetails user,
                               @RequestParam String name) {
        workspaceService.createWorkspace(user.getUsername(), name);
        return "redirect:/home";
    }

    @GetMapping("/workspace/{id}")
    public String viewWorkspace(@PathVariable Long id,
                                @AuthenticationPrincipal UserDetails user,
                                Model model) {
        Workspace workspace = workspaceService.getWorkspaceById(id);

        // Optional: Zugriffsschutz
        if (!workspace.getOwnerUsername().equals(user.getUsername())) {
            return "error/403"; // oder eine andere Fehlerseite
        }

        model.addAttribute("workspace", workspace);
        return "workspace-view"; // deine View-Datei
    }
    @PostMapping("/workspace/{id}/bucket")
    public String addBucket(@PathVariable Long id,
                            @RequestParam String name,
                            @RequestParam BigDecimal amount) {
        bucketService.addBucketToWorkspace(id, name, amount);
        return "redirect:/workspace/" + id;
    }

    @PostMapping("/workspace/{id}/delete")
    public String deleteWorkspace(@PathVariable Long id, Principal principal) {
        // Optional: Prüfe, ob der eingeloggte Nutzer auch Besitzer des Workspaces ist
        Workspace workspace = workspaceService.getWorkspaceById(id);
        if (workspace == null || !workspace.getUser().getUsername().equals(principal.getName())) {
            // Kein Zugriff oder Workspace existiert nicht
            return "redirect:/workspace?error=accessDenied";
        }

        workspaceService.deleteById(id);
        return "redirect:/home";  // Zurück zur Workspace-Übersicht
    }
}
