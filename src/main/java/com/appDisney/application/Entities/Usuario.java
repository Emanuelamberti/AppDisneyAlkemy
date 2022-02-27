package com.appDisney.application.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="User")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userName;

    @Column(unique = true)
    private String email;

    private String encrypted; //clave

    @OneToOne
    private Image profilePicture;

    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;

    public Usuario() {
    }

    public Usuario(Integer id, String userName, String email, String encrypted, Image profilePicture, Date alta, Date baja) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.encrypted = encrypted;
        this.profilePicture = profilePicture;
        this.alta = alta;
        this.baja = baja;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public Date getBaja() {
        return baja;
    }

    public void setBaja(Date baja) {
        this.baja = baja;
    }
}
