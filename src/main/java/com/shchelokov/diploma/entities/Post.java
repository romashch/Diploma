package com.shchelokov.diploma.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class Post implements Comparable<Post> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String text;

    private Date datetime;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> comments;

    public Post() {

    }

    public Post(String title, String text) {
        this.title = title;
        this.text = text;
        this.datetime = Date.from(Instant.now());
        this.comments = new ArrayList<>();
    }

    public static String highlightingLinks(String s) {
        Pattern pattern = Pattern.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:\'\".,<>???“”‘’]))");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            if (matcher.group(1).startsWith("https://") || matcher.group(1).startsWith("http://")) {
                return matcher.replaceAll("<a href=\"$1\">$1</a>");
            } else {
                return matcher.replaceAll("<a href=\"https://$1\">$1</a>");
            }
        } else {
            return s;
        }
    }

    public static String textWithTags(String s) {
        return highlightingLinks(s).replace("\r\n", "<br/>");
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Attachment> getAttachments() {
        Collections.sort(attachments);
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getTitleWithTags() {
        return highlightingLinks(title);
    }

    public String getTextWithTags() {
        return highlightingLinks(text).replace("\r\n", "<br/>");
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getDatetimeStr() {
        final DateFormat df = new SimpleDateFormat("dd MMM yyyy 'в' HH:mm");
        return df.format(datetime);
    }

    public List<Comment> getComments() {
        Collections.sort(comments);
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public int compareTo(Post o) {
        return o.datetime.compareTo(datetime);
    }
}
