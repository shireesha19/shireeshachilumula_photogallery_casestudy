package com.shireesha.casestudy.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 45)
	@Size(min = 2, max = 20,message = "Email is mandatory")
	@NotNull
	private String email;
	
	@Column(nullable = false, length = 64)
	private String password;
	
	@Column(name = "first_name", nullable = false, length = 20)
	@Size(min = 2, max = 20 ,message = "First Name is mandatory")
	@NotNull
	private String firstName;
	
	@Column(name = "last_name", nullable = false, length = 20)
	@Size(min = 2, max = 20,message = "Last Name is mandatory")
	@NotNull
	private String lastName;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(
			name = "user_galleries",
			joinColumns = { @JoinColumn(name = "user_id") },
			inverseJoinColumns = { @JoinColumn(name = "gallery_id") })
	private List<Gallery> userGalleries = new ArrayList<>();

	public List<Photo> getUserPhotos() {
		return userPhotos;
	}

	public void setUserPhotos(List<Photo> userPhotos) {
		this.userPhotos = userPhotos;
	}

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(
			name = "user_photos",
			joinColumns = { @JoinColumn(name = "user_id") },
			inverseJoinColumns = { @JoinColumn(name = "photo_id") })
	private List<Photo> userPhotos = new ArrayList<>();

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinTable( name = "user_settings",
			joinColumns = { @JoinColumn(name =
					"user_id") }, inverseJoinColumns = { @JoinColumn(name = "settings_id") })
	private Settings settings ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Gallery> getUserGalleries() {
		return userGalleries;
	}

	public void setUserGalleries(List<Gallery> userGalleries) {
		this.userGalleries = userGalleries;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}
}
