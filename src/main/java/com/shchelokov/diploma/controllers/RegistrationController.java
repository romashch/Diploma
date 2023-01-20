package com.shchelokov.diploma.controllers;

import com.shchelokov.diploma.entities.Role;
import com.shchelokov.diploma.entities.User;
import com.shchelokov.diploma.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("error", "Такой пользователь уже зарегистрирован.");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }
}
