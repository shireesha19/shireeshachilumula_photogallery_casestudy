package com.shireesha.casestudy.models;

import javax.persistence.*;

@Entity
@Table(name = "settings")
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public boolean isEnableSharing() {
        return enableSharing;
    }

    public void setEnableSharing(boolean enableSharing) {
        this.enableSharing = enableSharing;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(nullable = false, unique = false)
    private boolean enableSharing;

    @OneToOne(mappedBy = "settings")
    private User user ;
}
