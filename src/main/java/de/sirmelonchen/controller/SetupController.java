package de.sirmelonchen.controller;

import de.sirmelonchen.model.Setup;
import de.sirmelonchen.service.UserService;
import de.sirmelonchen.status.SetupStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/setup")
public class SetupController {

    private final SetupStatus setupStatus;
    private final UserService userService;

    public SetupController(SetupStatus setupStatus, UserService userService) {
        this.setupStatus = setupStatus;
        this.userService = userService;
    }
    @GetMapping
    public String setup(Model model){
        if(!Boolean.parseBoolean(System.getenv("SETUP"))){
            return "setup/setup";
        }
        else {
            return "redirect:/";
        }
    }
    @PostMapping
    public String endSetup(Setup request){
        userService.registerUser(request.getAdminUsername(), request.getAdminPassword(), request.getAdminEmail());
        setupStatus.setSetupCompleted(true);
        return "redirect:/";
    }
}
