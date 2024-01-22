package com.student_grievance_portal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student_grievance_portal.entity.Admin;
import com.student_grievance_portal.entity.NewGrievance;
import com.student_grievance_portal.entity.NewUser;
import com.student_grievance_portal.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	
@Autowired
private UserRepository userRepo;

public void save(NewUser user) {
    try {
    	user.setSubmissionTime(LocalDateTime.now());
        userRepo.save(user);
        // Add some logging if needed
        System.out.println("User saved successfully: " + user.getName());
    } catch (Exception e) {
        // Handle the exception appropriately (log it, throw it, etc.)
        System.err.println("Error saving user: " + e.getMessage());
    }
}
//public NewUser getUserByRegNo(String regNo) {
//    return userRepo.findByRegno(regNo);
//}

public List<NewUser> getAllUsers() {
    return userRepo.findAll();
}

public NewUser getUserById(Long id) {
    return userRepo.findById(id).orElse(null);
}

}
