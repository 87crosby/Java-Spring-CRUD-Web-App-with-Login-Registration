package com.howe.belt.models;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="Username is required")
	@Size(min = 3, max = 30, message="Username must be between 3 and 30 characters")
	private String username;
	
	@NotEmpty(message = "Email is required")
	@Email(message = "Please enter a valid email")
	private String email;
	
	@NotEmpty(message = "Password is required")
	@Size(min=8, max=128, message = "Password must be between 8 and 128 characters")
	private String password;
	
	@Transient
	@NotEmpty(message = "Confirm password is required")
	@Size(min=8, max=128, message = "Password must be between 8 and 128 characters")
	private String confirm;
	
	
	// One to many with meals table
	@OneToMany(mappedBy="uploader", fetch=FetchType.LAZY)
	private List<Meal> mealsUploaded;
	
	// Time-stamp fields
	@Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
	
	// Empty constructor
	public User() {
	}
	
	
	// Loaded constructor
	public User(String username, String email, String password) {
		this.username=username;
		this.email=email;
		this.password=password;
	}
	
	// Time-stamps 
	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
    
    // Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Meal> getMealsUploaded() {
		return mealsUploaded;
	}

	public void setMealsUploaded(List<Meal> mealsUploaded) {
		this.mealsUploaded = mealsUploaded;
	}
    
	
	
	

}