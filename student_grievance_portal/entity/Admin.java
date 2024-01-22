package com.student_grievance_portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable=false)
    private String name;
public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
@Column(name="email", nullable=false)
    private String email;
@Column(name="password", nullable=false)
    private String password;
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
	public Admin(String email, String password,String name) {
		super();
		this.email = email;
		this.password = password;
		this.name=name;
	}
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean isPasswordMatch(String rawPassword) {
        // Assuming this.password contains the raw password stored in the database
        return this.password.equals(rawPassword);
    }

    
}
