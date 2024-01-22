package com.student_grievance_portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student_grievance_portal.entity.Admin;

import com.student_grievance_portal.repository.AdminRepository;

@Service
public class AdminService {

	
	@Autowired
	private AdminRepository adminRepo;
	
	public Admin getAdminById(Long id) {
        return adminRepo.findById(id).orElse(null);
    }
	
	public List<Admin> getAdmin() {
        return adminRepo.findAll();
    }
	 public void saveAdmin(Admin admin) {
	        adminRepo.save(admin);
	    }
	 
	 public void deleteAdmin(Long id) {
	        adminRepo.deleteById(id);
	    }
}
