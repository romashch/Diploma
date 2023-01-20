package com.shchelokov.diploma.controllers;

import com.shchelokov.diploma.entities.Role;
import com.shchelokov.diploma.entities.User;
import com.shchelokov.diploma.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userEditForm(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam("userId") User user
    ) {
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return "redirect:/blog";
    }
}
