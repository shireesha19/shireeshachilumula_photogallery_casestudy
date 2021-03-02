package com.shireesha.casestudy.models;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "photos")
public class Photo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false, length = 45)
    private String name;

    @Column(nullable = true, unique = false, length = 45)
    private String location;

    @Column(name = "taken_on")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date takenOn;

    @ManyToMany(mappedBy = "galleryPhotos")
    private Set<Gallery> galleries ;

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @ManyToMany(mappedBy = "userPhotos")
    private Set<User> users = new HashSet<>();

    public Photo() {
        this.galleries=new HashSet<>();
    }

    @Lob
    private Byte[] image;

    @Column(nullable = false, unique = false)
    private boolean isCoverPhoto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String name) {
        this.location = name;
    }

    public Date getTakenOn() {
        return takenOn;
    }

    public void setTakenOn(Date takenOn) {
        this.takenOn = takenOn;
    }


    public Set<Gallery> getGalleries() { return galleries; }

    public void setGalleries(Set<Gallery> galleries) { this.galleries =
            galleries; }


    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public boolean isCoverPhoto() {
        return isCoverPhoto;
    }

    public void setCoverPhoto(boolean coverPhoto) {
        isCoverPhoto = coverPhoto;
    }


}
