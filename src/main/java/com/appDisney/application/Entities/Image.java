package com.appDisney.application.Entities;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String mime;

    @Lob @Basic(fetch=FetchType.LAZY)
    private byte[] content;

    public Image() {
    }

    public Image(Integer id, String name, String mime, byte[] content) {
        this.id = id;
        this.name = name;
        this.mime = mime;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
