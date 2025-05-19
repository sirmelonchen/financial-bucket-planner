package de.sirmelonchen.controller;

import de.sirmelonchen.model.Workspace;
import de.sirmelonchen.service.UserService;
import de.sirmelonchen.service.WorkspaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Controller
public class UserController {

    private final UserService userService;
    private final Map<String, List<Workspace>> userWorkspaces = new HashMap<>();
    private final WorkspaceService workspaceService;

    public UserController(UserService userService, WorkspaceService workspaceService) {
        this.userService = userService;
        this.workspaceService = workspaceService;
    }


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
        userService.registerUser(username, password);
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
}
