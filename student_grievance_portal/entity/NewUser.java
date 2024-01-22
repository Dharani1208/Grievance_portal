package com.student_grievance_portal.entity;

import java.time.LocalDateTime;


//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="user_details")

public class NewUser {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
//	private String regno;
	@Column(name="course", nullable=false)
	private String course;
	@Column(name="type", nullable=false)
	private String type;
//	private String year;
	
	@Column(name="email", nullable=false)
	private String email;
	@Column(name="mobile", nullable=false)
	private Long mobile;
	@Column(name="password", nullable=false)
	private String password;
	@Transient
	private String cpassword;
	
	@Column(name = "createdAt", nullable=false)
    private LocalDateTime submissionTime;
	
	
	public LocalDateTime getSubmissionTime() {
		return submissionTime;
	}

//	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public void setSubmissionTime(LocalDateTime submissionTime) {
		this.submissionTime = submissionTime;
	}

	
	public NewUser(String name, String course, String type, String email, Long mobile, String password, String cpassword,
		LocalDateTime submissionTime) {
	super();
	this.name = name;
	this.course = course;
	this.type = type;
	this.email = email;
	this.mobile = mobile;
	this.password = password;
	this.cpassword = cpassword;
	this.submissionTime = submissionTime;
}


//	public String getDeg() {
//		return deg;
//	}
//
//	public void setDeg(String deg) {
//		this.deg = deg;
//	}
//
//	public String getDept() {
//		return dept;
//	}
//
//	public void setDept(String dept) {
//		this.dept = dept;
//	}
//
//	public String getYear() {
//		return year;
//	}
//
//	public void setYear(String year) {
//		this.year = year;
//	}

	public String getEmail() {
		return email;
	}

	
	public String getCourse() {
		return course;
	}


	public void setCourse(String course) {
		this.course = course;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
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

//	public String getRegno() {
//		return regno;
//	}
//  
//	public void setRegno(String regno) {
//		this.regno = regno;
//	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
	    this.password =password;
	}


	public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	 
	    
//	    public boolean isPasswordMatch(String rawPassword) {
//	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//	        return passwordEncoder.matches(rawPassword, this.password);
//	    }
	    
//	    public boolean isPasswordMatch(String rawPassword) {
//	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//	        
//	        System.out.println("Entered Password Hash in isPasswordMatch: " + passwordEncoder.encode(rawPassword));
//	        System.out.println("Stored Password Hash in isPasswordMatch: " + this.password);
//
//	        return passwordEncoder.matches(rawPassword, this.password);
//	    }
	    public boolean isPasswordMatch(String rawPassword) {
	        // Assuming this.password contains the raw password stored in the database
	        return this.password.equals(rawPassword);
	    }


	public NewUser() {
		super();
	}
}
