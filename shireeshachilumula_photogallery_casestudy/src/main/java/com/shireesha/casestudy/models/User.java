package com.shireesha.casestudy.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.shireesha.casestudy.annotations.EmailConstraint;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unused")
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	//@Size(min = 2, max = 20,message = "Email is mandatory")
	//@EmailConstraint
	//regular expression for email
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="Email address is invalid")
	private String email;
	
	@Column(nullable = false)
	
	@Size(min = 6, max = 100,message = "Password must be between 6 and 20 characters")
	@NotNull(message = "Password must be between 6 and 20 characters")
	
	private String password;
	
	@Column(name = "first_name", nullable = false)
	@Size(min = 2, max = 20 ,message = "First Name must be between 2 and 20 characters")
	@NotNull
	private String firstName;
	
	@Column(name = "last_name", nullable = false, length = 20)
	@Size(min = 2, max = 20,message = "Last Name must be between 2 and 20 characters")
	@NotNull
	private String lastName;

	//joining users and galleries table
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(
			name = "user_galleries",
			joinColumns = { @JoinColumn(name = "user_id") },
			inverseJoinColumns = { @JoinColumn(name = "gallery_id") })
	private List<Gallery> userGalleries = new ArrayList<>();

	//joining users and photos table

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(
			name = "user_photos",
			joinColumns = { @JoinColumn(name = "user_id") },
			inverseJoinColumns = { @JoinColumn(name = "photo_id") })
	private List<Photo> userPhotos = new ArrayList<>();

	//joining users and settings table
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinTable( name = "user_settings",
			joinColumns = { @JoinColumn(name =
					"user_id") }, inverseJoinColumns = { @JoinColumn(name = "settings_id") })
	private Settings settings ;

	
	public List<Photo> getUserPhotos() {
		return userPhotos;
	}

	public void setUserPhotos(List<Photo> userPhotos) {
		this.userPhotos = userPhotos;
	}
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
