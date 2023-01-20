package com.shchelokov.diploma.entities;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Entity
public class Comment implements Comparable<Comment> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private Date datetime;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment() {

    }

    public Comment(String text, Post post, User user) {
        this.text = text;
        this.post = post;
        this.user = user;
        this.datetime = Date.from(Instant.now());
    }

    public String author() {
        return user.getUsername();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDatetimeStr() {
        final DateFormat df = new SimpleDateFormat("dd MMM yyyy 'в' HH:mm");
        return df.format(datetime);
    }

    public String getTextWithTags() {
        return Post.highlightingLinks(text).replace("\r\n", "<br/>");
    }

    @Override
    public int compareTo(Comment o) {
        return o.datetime.compareTo(datetime);
    }
}
