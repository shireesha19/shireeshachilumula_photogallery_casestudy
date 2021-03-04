package com.shireesha.casestudy.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "galleries")
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false, length = 45)
    @Size(min = 3, max = 45)
    private String name;

    @Column(nullable = true, unique = true)
    private Long coverPhotoId;


    @ManyToMany(mappedBy = "userGalleries")
    private Set<User> users = new HashSet<>();

 //joining galleries and photos table
    @ManyToMany
    @JoinTable( name = "photo_galleries",
            joinColumns = { @JoinColumn(name =
                    "gallery_id") }, inverseJoinColumns = { @JoinColumn(name = "photo_id") })
    private List<Photo> galleryPhotos ;


    public Gallery() {

        this.galleryPhotos=new ArrayList<>();
    }


    public Gallery(Long id, String name, Long coverPhotoId, List<Photo> galleryPhotos) {
        super();
        this.id = id;
        this.name = name;
        this.coverPhotoId = coverPhotoId;
        this.galleryPhotos = galleryPhotos;
    }


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


    public List<Photo> getGalleryPhotos() { return galleryPhotos; }

    public void setGalleryPhotos(List<Photo> photoGalleries) { this.galleryPhotos
            = photoGalleries; }


    public Set<User> getUsers() { return users; }

    public void setUsers(Set<User> users) { this.users = users; }


    public Long getCoverPhotoId() {
        return coverPhotoId;
    }

    public void setCoverPhotoId(Long coverPhotoId) {
        this.coverPhotoId = coverPhotoId;
    }
}
