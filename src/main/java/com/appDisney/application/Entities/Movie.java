package com.appDisney.application.Entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Image image;

    private String title;

    private String gender;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date creationDate;

    private Double qualification;

    @ManyToMany(mappedBy = "movies")
    private List<Personaje> personajes;

    public Movie() {
    }

    public Movie(Integer id, Image image, String title, String gender, Date creationDate, Double qualification, List<Personaje> personajes) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.gender = gender;
        this.creationDate = creationDate;
        this.qualification = qualification;
        this.personajes = personajes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Double getQualification() {
        return qualification;
    }

    public void setQualification(Double qualification) {
        this.qualification = qualification;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<Personaje> personajes) {
        this.personajes = personajes;
    }
}
