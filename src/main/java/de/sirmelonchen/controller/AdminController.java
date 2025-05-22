package de.sirmelonchen.controller;

import de.sirmelonchen.model.User;
import de.sirmelonchen.model.Workspace;
import de.sirmelonchen.repository.UserRepository;
import de.sirmelonchen.repository.WorkspaceRepository;
import de.sirmelonchen.service.EmailService;
import de.sirmelonchen.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    private final EmailService emailService;

    public AdminController(UserService userService, UserRepository userRepository , WorkspaceRepository workspaceRepository, EmailService emailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
        this.emailService = emailService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String listUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/user.html";  // Name der Thymeleaf Vorlage
    }


    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/toggle")
    public String toggleUserStatus(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User nicht gefunden"));
        user.setEnabled(!user.isEnabled()); // oder user.setLocked(!user.isLocked());
        userRepository.save(user);
        String link = "http://localhost:8080/login";
        if (!user.isEnabled()){
            String html = "<h3>Dein Konto wurde deaktiviert!</h3>" +
                    "<p>Dein Konto wurde von einem Administrator deaktiviert.:</p>" +
                    "<a href=\"" + link + "\"><button>Login-Seite</button></a>";

            emailService.send(user.getEmail(), "Konto Deaktiviert", html);
        } else if (user.isEnabled()) {
            String html = "<h3>Dein Konto wurde aktiviert!</h3>" +
                    "<p>Dein Konto wurde von einem Administrator aktiviert.:</p>" +
                    "<a href=\"" + link + "\"><button>Login-Seite</button></a>";

            emailService.send(user.getEmail(), "Konto Aktiviert", html);
        }

        return "redirect:/admin/users";
    }

    @GetMapping("/user-workspaces/{userId}")
    public String showUserWorkspaces(@PathVariable Long userId,
                                     @RequestParam(name = "showNumbers", defaultValue = "false") boolean showNumbers,
                                     Model model) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige User-ID"));

        List<Workspace> workspaces = workspaceRepository.findByUserId(userId);

        model.addAttribute("user", user);
        model.addAttribute("workspaces", workspaces);
        model.addAttribute("showNumbers", showNumbers);
        return "admin/user-workspaces";  // Name des Templates (resources/templates/admin/user-workspaces.html)
    }



}
