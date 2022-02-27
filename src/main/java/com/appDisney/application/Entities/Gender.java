package com.appDisney.application.Entities;

import javax.persistence.*;

@Entity
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToOne
    private Image image;

    @OneToOne
    private Movie movie;

    public Gender() {
    }

    public Gender(Integer id, String name, Image image, Movie movie) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.movie = movie;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
