package com.appDisney.application.Entities;

import javax.persistence.*;
import javax.persistence.GeneratedValue;

import java.util.List;

@Entity
@Table(name="Personaje")
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer age;

    private Double weight;

    private String history;

    @OneToOne
    private Image image;

    @ManyToMany
    @JoinTable(name= "associatedCharacters",
            joinColumns = @JoinColumn(name ="movie_id"),
            inverseJoinColumns= @JoinColumn(name="character_id"))
    private List<Movie> movies;

    public Personaje() {
    }

    public Personaje(Integer id, String name, Integer age, Double weight, String history, Image image, List<Movie> movies) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.history = history;
        this.image = image;
        this.movies = movies;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
