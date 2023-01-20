package com.shchelokov.diploma.controllers;

import com.shchelokov.diploma.entities.Attachment;
import com.shchelokov.diploma.entities.Description;
import com.shchelokov.diploma.entities.User;
import com.shchelokov.diploma.repos.DescRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/desc")
@PreAuthorize("hasAuthority('ADMIN')")
public class DescriptionController {

    @Autowired
    private DescRepo descRepo;

    @GetMapping
    public String desc(@AuthenticationPrincipal User user, Model model) {
        Description desc = descRepo.findAll().iterator().next();
        model.addAttribute("desc", desc);
        model.addAttribute("user", user);
        return "desc-edit";
    }

    private byte[] buffer;

    @PostMapping
    public String descEdit(@RequestParam String fullTitle,
                           @RequestParam String description,
                           @RequestParam MultipartFile intro,
                           @AuthenticationPrincipal User user,
                           Model model) {
        model.addAttribute("user", user);
        Description desc = descRepo.findAll().iterator().next();
        String error = setIntro(intro);
        if (error.equals("")) {
            desc.setFullTitle(fullTitle);
            desc.setDescription(description);
            desc.setFileName(intro.getOriginalFilename());
            if (buffer != null) {
                desc.setIntro(buffer);
            }
            descRepo.save(desc);
            return "redirect:/blog";
        } else {
            model.addAttribute("desc", desc);
            model.addAttribute("error", error);
            return "desc-edit";
        }
    }

    private String setIntro(MultipartFile intro) {
        String fileName = intro.getOriginalFilename();
        String res = "";
        try {
            if (!fileName.isEmpty()) {
                if (!isImg(intro))
                    res = "Интро должно иметь расширение jpg, jpeg или png.";
                else
                    buffer = intro.getBytes();
            }
        } catch (IOException ex) {
            res = "Не удалось загрузить изображение " + fileName + ".";
        }
        return res;
    }

    public String getExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    public boolean isImg(MultipartFile file) {
        return (getExtension(file).equals("jpg") || getExtension(file).equals("jpeg") || getExtension(file).equals("png"));
    }
}
