package com.shchelokov.diploma.controllers;

import com.shchelokov.diploma.entities.Attachment;
import com.shchelokov.diploma.entities.Description;
import com.shchelokov.diploma.entities.Post;
import com.shchelokov.diploma.repos.AttachmentRepository;
import com.shchelokov.diploma.repos.DescRepo;
import com.shchelokov.diploma.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private DescRepo descRepo;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        Description desc = descRepo.findAll().iterator().next();
        model.addAttribute("desc", desc);
        model.addAttribute("posts", sort(posts.iterator()));
        return "home";
    }

    public static List<Post> sort(Iterator<Post> it) {
        List<Post> list = new ArrayList<>();
        while (it.hasNext()) {
            list.add(it.next());
        }
        Collections.sort(list);
        return list;
    }

    @ResponseBody
    @GetMapping(value = "/uploaded/{id}.{ext}")
    public byte[] file(@PathVariable(value = "id") long id,
                       @PathVariable(value = "ext") String ext,
                       HttpServletResponse response) throws IOException {
        Optional<Attachment> attachmentSearchRes = attachmentRepository.findById(id);
        if (attachmentSearchRes.isPresent()) {
            Attachment attachment = attachmentSearchRes.get();
            if (attachment.getFile() != null && attachment.getFile().length > 0) {
                byte[] file = attachment.getFile();
                if (attachment.isImg()) {
                    response.setContentType("image/" + ext);
                } else {
                    response.setContentType("application/" + ext);
                }
                response.setContentLength(file.length);
                response.getOutputStream().write(file);
                return file;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File Not Found");
    }

    @ResponseBody
    @GetMapping(value = "/uploaded/intro.{ext}")
    public byte[] file(@PathVariable(value = "ext") String ext,
                       HttpServletResponse response) throws IOException {
        byte[] file = descRepo.findAll().iterator().next().getIntro();
        response.setContentType("image/" + ext);
        response.setContentLength(file.length);
        response.getOutputStream().write(file);
        return file;
    }
}
