package com.student_grievance_portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student_grievance_portal.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin findByEmail(String username);
	Optional<Admin> findById(Long id);

}
