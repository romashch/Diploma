package com.shchelokov.diploma.controllers;

import com.shchelokov.diploma.entities.Attachment;
import com.shchelokov.diploma.entities.Description;
import com.shchelokov.diploma.entities.Post;
import com.shchelokov.diploma.entities.User;
import com.shchelokov.diploma.repos.AttachmentRepository;
import com.shchelokov.diploma.repos.DescRepo;
import com.shchelokov.diploma.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private DescRepo descRepo;

    @GetMapping("/blog")
    public String blogMain(
            @AuthenticationPrincipal User user,
            Model model) {
        Iterable<Post> posts = postRepository.findAll();
        Description desc = descRepo.findAll().iterator().next();
        model.addAttribute("desc", desc);
        model.addAttribute("posts", MainController.sort(posts.iterator()));
        model.addAttribute("user", user);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String blogAdd(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "blog-add";
    }

    private List<Attachment> buffer;

    @PostMapping("/blog/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String text,
                              @RequestParam("files") MultipartFile[] files,
                              @AuthenticationPrincipal User user,
                              Model model) {
        model.addAttribute("user", user);
        Post post = new Post(title, text);
        buffer = new ArrayList<>();
        String error = createAttachments(files, post);
        if (text.equals("") && buffer.size() == 0) {
            model.addAttribute("error", "Содержимое поста (текст и вложения) не может быть пустым.");
            return "blog-add";
        }
        if (!error.equals("")) {
            model.addAttribute("error", error);
            return "blog-add";
        }
        post.setAttachments(buffer);
        postRepository.save(post);
        return "redirect:/blog";
    }

    private String createAttachments(MultipartFile[] files, Post post) {
        int count = 0;
        for (int i = 0; i < files.length && count != 10; i++) {
            try {
                if (!files[i].getOriginalFilename().isEmpty()) {
                    Attachment attachment = new Attachment(files[i].getBytes(), files[i].getOriginalFilename(), post);
                    buffer.add(attachment);
                    count++;
                }
            } catch (IOException ex) {
                return "Не удалось загрузить файл " + files[i].getOriginalFilename() + ".";
            }
        }
        if (count == 10 && files.length > count) {
            return "Число вложений не должно превышать 10.";
        }
        return "";
    }

    @GetMapping("/blog/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String blogEdit(@AuthenticationPrincipal User user, @PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        model.addAttribute("user", user);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String blogPostUpdate(@PathVariable(value = "id") long id,
                                 @RequestParam String title,
                                 @RequestParam String text,
                                 @RequestParam MultipartFile[] files,
                                 @AuthenticationPrincipal User user,
                                 Model model) {
        model.addAttribute("user", user);
        Post post = postRepository.findById(id).orElseThrow();
        buffer = new ArrayList<>();
        String error = createAttachments(files, post);
        if (text.equals("") && buffer.size() == 0) {
            model.addAttribute("post", post);
            model.addAttribute("error", "Содержимое поста (текст и вложения) не может быть пустым.");
            return "blog-edit";
        }
        if (!error.equals("")) {
            model.addAttribute("post", post);
            model.addAttribute("error", error);
            return "blog-edit";
        }

        post.setAttachments(buffer);
        attachmentRepository.findAll().forEach(att -> {
            if (att.getPost().getId().equals(post.getId())) {
                attachmentRepository.deleteById(att.getId());
            }
        });

        post.setTitle(title);
        post.setText(text);

        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }
}
