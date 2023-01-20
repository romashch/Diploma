package com.shchelokov.diploma.entities;

import javax.persistence.*;

@Entity
public class Attachment implements Comparable<Attachment> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private byte[] file;

    private String fileName;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;

    public Attachment() {

    }

    public Attachment(byte[] file, String fileName, Post post) {
        this.file = file;
        this.fileName = fileName;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getExtension() {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    public boolean isImg() {
        return (getExtension().equals("jpg") || getExtension().equals("jpeg") || getExtension().equals("png"));
    }

    @Override
    public int compareTo(Attachment o) {
        if (isImg() && !o.isImg()) return 1;
        if (!isImg() && o.isImg()) return -1;
        return 0;
    }
}
