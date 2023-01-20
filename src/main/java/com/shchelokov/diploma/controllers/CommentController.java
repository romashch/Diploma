package com.shchelokov.diploma.controllers;

import com.shchelokov.diploma.entities.Comment;
import com.shchelokov.diploma.entities.Post;
import com.shchelokov.diploma.entities.User;
import com.shchelokov.diploma.repos.CommentRepository;
import com.shchelokov.diploma.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/blog/{id}/comment/add")
    public String addComment(@PathVariable(value = "id") long id,
                             @RequestParam String text,
                             @AuthenticationPrincipal User user,
                             Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Post post = postRepository.findById(id).orElse(new Post());
        Comment comment = new Comment(text, post, user);
        commentRepository.save(comment);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/comment/{id2}/remove")
    public String removeComment(
            @PathVariable(value = "id") long id,
            @PathVariable(value = "id2") long id2,
            Model model
    ) {
        if (postRepository.existsById(id)) {
            Comment comment = commentRepository.findById(id2).orElseThrow();
            Post post = postRepository.findById(id).orElseThrow();
            List<Comment> newCommentList = new ArrayList<>();
            for (Comment c : post.getComments()) {
                if (c != comment) {
                    newCommentList.add(c);
                }
            }
            post.setComments(newCommentList);
            commentRepository.delete(comment);
        }
        return "redirect:/blog";
    }
}
