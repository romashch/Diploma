package com.shchelokov.diploma.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Description {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullTitle;
    private String description;
    private byte[] intro;
    private String fileName;

    public Description() {
    }

    public Description(String fullTitle, String description, byte[] intro, String fileName) {
        this.fullTitle = fullTitle;
        this.description = description;
        this.intro = intro;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getDescWithTags() {
        return Post.textWithTags(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getIntro() {
        return intro;
    }

    public void setIntro(byte[] intro) {
        this.intro = intro;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String extension() {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
}
